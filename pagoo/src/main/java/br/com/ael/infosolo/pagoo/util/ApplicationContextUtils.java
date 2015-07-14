package br.com.ael.infosolo.pagoo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Para manipular contexto spring.
 * @author David Faulstich (davidfdr@gmail.com
 * @since 02/07/2015
 *
 */
public class ApplicationContextUtils implements ApplicationContextAware {

	private static ApplicationContext ctx = null;

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
}
