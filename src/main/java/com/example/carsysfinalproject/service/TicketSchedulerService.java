package com.example.carsysfinalproject.service;

import com.example.carsysfinalproject.model.core.entity.reservation.Ticket;
import com.example.carsysfinalproject.model.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TicketSchedulerService {

    @Autowired
    private TicketRepository ticketRepository;

    // @Scheduled(fixedDelay = 900000) -> 15 minutes
    @Scheduled(fixedDelay = 10000) // 10 seconds
    public void checkPendingTickets() {
        List<Ticket> pendingTickets = ticketRepository.findPendingTickets("Pending");
        LocalTime now = LocalTime.now();
        for (Ticket ticket : pendingTickets) {
            if (ticket.getToTime().isBefore(now)) {
                ticket.setTicketStatus("Abandoned");
                System.out.println("Ticket " + ticket.getTicketId() + " has been abandoned.");
                ticketRepository.save(ticket);
            }
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateTicketsForAllUsers() {
        List<Integer> userIds = ticketRepository.findAllUserIdsWithActiveTickets();
        LocalTime now = LocalTime.now();
        LocalTime nextHour = now.plusHours(1);

        for (int userId : userIds) {
            List<Ticket> activeTickets = ticketRepository.findByUserIdAndTicketStatus(userId, "Active");

            if (!activeTickets.isEmpty()) {
                Ticket currentTicket = activeTickets.get(0);

                LocalTime endTime = currentTicket.getToTime();

                if (endTime.isBefore(now) || endTime.equals(now)) {
                    List<Ticket> nextHourTickets = ticketRepository.findNextHourPendingTicketsForUser("Pending", userId);

                    if (!nextHourTickets.isEmpty()) {
                        Ticket nextTicket = nextHourTickets.get(0);

                        currentTicket.setTicketStatus("Finished");
                        nextTicket.setTicketStatus("Active");

                        System.out.println("Ticket " + currentTicket.getTicketId() + " has been finished.");
                        System.out.println("Ticket " + nextTicket.getTicketId() + " has been activated.");

                        ticketRepository.save(currentTicket);
                        ticketRepository.save(nextTicket);
                    }
                }
            }
        }
    }
}
