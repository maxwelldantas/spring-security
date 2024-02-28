package com.github.maxwelldantas.springsecurity.controller;

import com.github.maxwelldantas.springsecurity.model.User;
import com.github.maxwelldantas.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping
	public void postUser(@RequestBody User user) {
		service.createUser(user);
	}
}
