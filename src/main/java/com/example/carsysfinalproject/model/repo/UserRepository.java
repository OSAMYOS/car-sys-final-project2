package com.example.carsysfinalproject.model.repo;

import com.example.carsysfinalproject.model.core.entity.reservation.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("delete from User u where u.id = :id")
    void deleteByUserId(int id);

    @Query("select u from User u where u.userRole = 'ADMIN'")
    List<User> findAllByAdminRole(String admin);

    @Query("select u from User u where u.userRole = 'GUARD'")
    List<User> findAllByGuardRole(String guard);

    @Query("select u from User u where u.userRole = 'STUDENT'")
    List<User> findAllByStudentRole(String student);

    @Query("select u from User u where u.userStatus = 'ACTIVE'")
    List<User> findAllActiveUsers(String active);

    @Query("select u from User u where u.userStatus = 'DELETED'")
    List<User> findAllDeletedUsers(String deleted);
}
