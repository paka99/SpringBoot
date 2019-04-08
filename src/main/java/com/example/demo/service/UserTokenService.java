package com.example.demo.service;

import com.example.demo.model.UserToken;
import com.example.demo.model.UserTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserTokenService {
	@Autowired
	UserTokenDao userTokenDao;

	public void saveOrUpdate(UserToken UserToken) {
		userTokenDao.save(UserToken);
	}

	public String registerUser(String username, String password){
		if (getUserTokenByUsername(username) != null) {
			return "Already Username";
		}

		UserToken userToken = UserToken.getInstance(username, password);
		saveOrUpdate(userToken);

		return userToken.getToken();
	}

	public String updateTokenGenCount(String token) {
		String username = UserToken.getUsernameByToken(token);
		UserToken userToken = getUserTokenByUsername(username);

		String newToken = userToken.addCountAndGetToken();
		userToken.setToken(newToken);
		saveOrUpdate(userToken);

		return newToken;
	}

	public UserToken getUserTokenByUsername(String username) {
		return userTokenDao.findByUsername(username);
	}

	public String getUserTokenByLogin(String username, String password) {
		UserToken userToken = getUserTokenByUsername(username);

		if ( userToken == null ) {
			return "no username";
		}

		if ( password.equals(userToken.getPassword()) ) {
			return userToken.getToken();
		}

		return "invalid password";
	}

	public Boolean validateToken(String token) {
		String username = UserToken.getUsernameByToken(token);
		UserToken userToken = getUserTokenByUsername(username);

		return userToken.verifyToken(token);
	}
}
