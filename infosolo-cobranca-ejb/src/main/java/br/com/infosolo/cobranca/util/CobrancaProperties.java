package br.com.infosolo.cobranca.util;

import java.io.IOException;

import br.com.infosolo.comum.util.BaseProperties;

public class CobrancaProperties extends BaseProperties {
	private static final long serialVersionUID = 7255222873230392448L;

	public CobrancaProperties() {
		try {
			this.load(CobrancaProperties.class.getResourceAsStream(Constantes.COBRANCA_PROPERTIES));
		}
		catch (IOException e) {
			//logger.error(ConstantesErros.ERROR_FILE_PROPERTIES + " [" + Constantes.COBRANCA_PROPERTIES + "]");
		}		
		
	}

}
