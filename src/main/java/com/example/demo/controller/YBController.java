package com.example.demo.controller;

import com.example.demo.model.UserSignInfo;
import com.example.demo.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class YBController {
	@Autowired
	UserTokenService userTokenService;


	@RequestMapping(value={"/signup", "/register"}, method = RequestMethod.POST)
	public String registerUserInfo(@RequestBody UserSignInfo userSignin) {
		return userTokenService.registerUser(userSignin.getUsername(), userSignin.getPassword());
	}

	@RequestMapping(value={"/signin", "/login"}, method = RequestMethod.PUT)
	public String loginUser(@RequestBody UserSignInfo userSignin) {
		return userTokenService.getUserTokenByLogin(userSignin.getUsername(), userSignin.getPassword());
	}

	@RequestMapping(value={"/token/refresh"}, method = RequestMethod.GET)
	public String refreshToken(@RequestHeader String authorization) {
		return userTokenService.updateTokenGenCount(authorization);
	}


	@RequestMapping(value={"/token/test"}, method = RequestMethod.GET)
	public String testAPI() {
		return "Authentication~";
	}
}
