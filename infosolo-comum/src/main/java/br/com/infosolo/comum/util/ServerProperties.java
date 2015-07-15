package br.com.infosolo.comum.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerProperties extends BaseProperties  {
	private static final long serialVersionUID = -7992062851627445705L;

	private static Logger logger = new Logger(ServerProperties.class.getName());

	public ServerProperties() {
		try {
			String path = System.getProperty("jboss.server.config.url") + "props/snr.properties";  
			URL url = new URL(path); 
			this.load(url.openStream());  
			
		} catch (MalformedURLException mue) {
			logger.error("Erro lendo propriedades do sistema.",mue);
			
		} catch (IOException ioe) {
			logger.error("Erro lendo propriedades do sistema.",ioe);
		}
	}
}
