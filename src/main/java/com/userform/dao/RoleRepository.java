package com.userform.dao;

import com.userform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Anna Likhachova
 */

public interface RoleRepository extends JpaRepository<Role, Long> {

}
