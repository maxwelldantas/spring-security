package com.github.maxwelldantas.springsecurity.init;

import com.github.maxwelldantas.springsecurity.model.User;
import com.github.maxwelldantas.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApplication implements CommandLineRunner {
	@Autowired
	private UserRepository repository;

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		User user = repository.findByUsername("admin");
		if (user == null) {
			user = new User();
			user.setName("ADMIN");
			user.setUsername("admin");
			user.setPassword("master123");
			user.getRoles().add("MANAGERS");
			repository.save(user);
		}
		user = repository.findByUsername("user");
		if (user == null) {
			user = new User();
			user.setName("USER");
			user.setUsername("user");
			user.setPassword("user123");
			user.getRoles().add("USERS");
			repository.save(user);
		}
	}
}
