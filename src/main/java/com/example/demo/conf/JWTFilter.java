package com.example.demo.conf;


import com.example.demo.service.UserTokenService;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class JWTFilter implements Filter {
	private UserTokenService userTokenService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader("Authorization");

		if ( !userTokenService.validateToken(header) ) {
			throw new ServletException("unAuthentication");
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	public void setUserTokenService(UserTokenService uts) {
		userTokenService = uts;
	}
}
