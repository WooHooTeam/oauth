package com.security.oauth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserDao")
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
