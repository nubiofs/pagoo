package br.com.ael.infosolo.pagoo.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.ael.infosolo.pagoo.data.UsuarioRepository;
import br.com.ael.infosolo.pagoo.exceptions.PagooAuthenticationException;
import br.com.ael.infosolo.pagoo.model.Usuario;
import br.com.ael.infosolo.pagoo.security.TokenAuthenticationService;
import br.com.ael.infosolo.pagoo.security.service.UserAuthentication;
import br.com.ael.infosolo.pagoo.util.ApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {
	


	protected StatelessLoginFilter(String defaultFilterProcessesUrl) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,	HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		AuthenticationManager authenticationManager = (AuthenticationManager) ApplicationContextUtils.getApplicationContext().getBean("authenticationManager");
		
		Usuario user = null;;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PagooAuthenticationException("Não foi possível autenticar.", e);
		}
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		
		return authenticationManager.authenticate(loginToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		
		TokenAuthenticationService tokenAuthenticationService = (TokenAuthenticationService) ApplicationContextUtils.getApplicationContext().getBean("tokenAuthenticationService");
		//final Usuario authenticatedUser = usuarioRepository.findByEmail(authentication.getPrincipal());
		Usuario user = (Usuario) authentication.getPrincipal();
		
		final UserAuthentication userAuthentication = new UserAuthentication(user);
		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Add the authentication to the Security context
	}
}