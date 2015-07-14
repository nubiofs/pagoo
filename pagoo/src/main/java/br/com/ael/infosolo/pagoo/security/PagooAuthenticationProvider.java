package br.com.ael.infosolo.pagoo.security;

import java.util.Collection;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import br.com.ael.infosolo.pagoo.model.Usuario;
import br.com.ael.infosolo.pagoo.service.UsuarioService;


/**
 * Pagoo autentication provider.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 02/07/2015
 *
 */
public class PagooAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		UsuarioService usuarioService = (UsuarioService) BeanProvider.getContextualReference("usuarioService");
		Usuario user = null;
		try {
			user = usuarioService.login(username, password);
			
			if(user == null){
				throw new BadCredentialsException("Erro ao efetuar login: ");	
			}
		} catch (Exception e) {

			throw new BadCredentialsException("Erro ao efetuar login: " + e.getMessage());
		}
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
