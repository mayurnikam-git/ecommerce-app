package com.example.springoauthdemo.dao;

import com.example.springoauthdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

       /* @Query("SELECT DISTINCT user FROM User user " +
        "INNER JOIN FETCH user.authorities AS authorities " +
        "WHERE user.username = :username")*/
        User findByUsername(/*@Param("username")*/ String username);
        }
