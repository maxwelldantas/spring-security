package com.github.maxwelldantas.springsecurity.repository;

import com.github.maxwelldantas.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
	User findByUsername(@Param("username") String username);

	boolean existsByUsername(String username);
}
