package br.com.ael.infosolo.pagoo.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.security.core.Authentication;

import br.com.ael.infosolo.pagoo.model.Usuario;
import br.com.ael.infosolo.pagoo.security.service.UserAuthentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TokenAuthenticationService {
	

	
	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
	private final TokenHandler tokenHandler = new TokenHandler();

	public void addAuthentication(HttpServletResponse response,	UserAuthentication authentication) {
		final Usuario user = authentication.getDetails();
		user.setExpires(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
		ObjectMapper mapper = new ObjectMapper();
		try {
			String userJson = mapper.writeValueAsString(user);
			response.getWriter().write(userJson);
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MediaType.APPLICATION_JSON);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final Usuario user = tokenHandler.parseUserFromToken(token);
			if (user != null) {
				return new UserAuthentication(user);
			}
		}
		return null;
	}
}
