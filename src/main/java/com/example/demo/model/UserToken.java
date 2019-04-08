package com.example.demo.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Entity
public class UserToken {
	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String password;
	private String token;
	private Integer count;

	private static Map jwtMap = new HashMap<String,Object>();
	static {
		jwtMap.put("alg","HS256");
		jwtMap.put("typ","JWT");
	}

	private static final String JWT_SECRET = "yunbin";

	public static UserToken getInstance(String username, String password) {
		UserToken userToken = new UserToken();
		userToken.username = username;
		userToken.password = password;
		userToken.count = 1;
		userToken.token = userToken.makeSigninToken();

		return userToken;
	}

	public static String getUsernameByToken(String token) {
		return JWT.decode(token).getClaim("username").asString();
	}


	public String makeSigninToken() {
		return JWT.create()
				.withHeader(jwtMap)
				.withClaim("username", username)
				.withClaim("count", count)
				.sign(HMAC512(JWT_SECRET));
	}

	public Boolean verifyToken(String token) {
		int count = JWT.decode(token).getClaim("count").asInt();

		if ( count != this.count ) {
			return false;
		}

		try {
			JWT.require(Algorithm.HMAC512(JWT_SECRET))
					.build()
					.verify(token);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String newToken){
		token = newToken;
	}

	public String getPassword(){
		return password;
	}


	public String addCountAndGetToken(){
		count += 1;
		return makeSigninToken();
	}
}

