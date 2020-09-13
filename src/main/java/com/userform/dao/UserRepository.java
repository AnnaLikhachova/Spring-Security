package com.userform.dao;

import com.userform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anna Likhachova
 */

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findUserByEmail(String email);

}
