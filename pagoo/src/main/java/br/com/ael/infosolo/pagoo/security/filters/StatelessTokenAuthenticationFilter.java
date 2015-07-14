package br.com.ael.infosolo.pagoo.security.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.com.ael.infosolo.pagoo.exceptions.PagooAuthenticationException;
import br.com.ael.infosolo.pagoo.security.TokenAuthenticationService;
import br.com.ael.infosolo.pagoo.util.ApplicationContextUtils;

/**
 * Filtro para autenticar por token! :)
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 02/07/2015
 *
 */
public class StatelessTokenAuthenticationFilter extends GenericFilterBean{
	
    
    

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		
		TokenAuthenticationService tokenAuthenticationService = (TokenAuthenticationService) ApplicationContextUtils.getApplicationContext().getBean("tokenAuthenticationService");

		Authentication authentication = null;
		try {
			authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) req);
			if(authentication!= null){
				//
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			res.getWriter().write("{\"msg\":\"Acesso nao autorizado\",\"detalhe\":\"" + e.toString() + "\"}");

		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}



}
