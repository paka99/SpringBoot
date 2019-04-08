package com.example.demo;

import com.example.demo.controller.YBController;
import com.example.demo.model.UserSignInfo;
import com.example.demo.service.UserTokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	UserTokenService userTokenService;

	@Autowired
	YBController ybController;

	private MockMvc mockMvc;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ybController).build();

		UserSignInfo userSignin = new UserSignInfo();
		userSignin.setUsername("유저1");
		userSignin.setPassword("1111");

		userTokenService.registerUser(userSignin.getUsername(), userSignin.getPassword());
	}

	@Test
	public void 유저_계정생성_토큰_Get() {
		UserSignInfo userSignin = new UserSignInfo();
		userSignin.setUsername("유저_2");
		userSignin.setPassword("2222");

		String token = userTokenService.registerUser(userSignin.getUsername(), userSignin.getPassword());
		System.out.println("계정 생성 후 토큰 : " + token);
	}

	@Test
	public void 유저_로그인_토큰_Get() {
		String token = userTokenService.getUserTokenByLogin("유저1", "1111");
		System.out.println("계정 로그인 후 토큰 : " + token);
	}

	@Test
	public void 토큰_Refresh() {
		String token = userTokenService.getUserTokenByLogin("유저1", "1111");
		token = userTokenService.updateTokenGenCount(token);

		System.out.println("토큰 새로고침 후 토큰 : " + token);
	}

	@Test
	public void 토큰_인증_API_호출() throws Exception {
		String token = userTokenService.getUserTokenByLogin("유저1", "1111");

		mockMvc.perform(get("/tokenn/test").header("Authorization" , token))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}


}
