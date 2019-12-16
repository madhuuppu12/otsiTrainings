package org.security.controller;

import org.security.service.UserDetailsServiceImpl;
import org.security.service.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	@GetMapping("/rahul")
	public String getRahul() {
		return "RAHUL LOAD";

	}

	@GetMapping("/sanjan")
	public String getSanjan() {
		return "Sanjan LOAD";

	}

	@Autowired
	private UserDetailsServiceImpl service;

	@PostMapping("/postData")
	public String postData(@RequestBody UserVo datails) {
		service.saveuser(datails);
		return "SUCCESS";

	}

}
