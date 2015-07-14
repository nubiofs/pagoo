package br.com.ael.infosolo.pagoo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WebFilter to configure response to UTF-8
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 01/07/2015
 *
 */
@WebFilter(urlPatterns = "/rest/*")
public class CharsetFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			// Do your logging here
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
