package br.com.infosolo.cobranca.negocio;

import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.comum.util.Logger;

public class BancoCAIXANegocio extends BancoNegocio {
	
	private static Logger logger = new Logger(BancoCAIXANegocio.class);
	
	
	public BancoCAIXANegocio() {
		banco = Banco.BANCO_CAIXA;
	}
	
	/**
	 * Formata o Nosso Numero com Digito Verificador
	 * @return
	 */
	@Override
	public String formatarNossoNumero(long codigoCedente, long sequencial, int tamanho) {
		
		//String nossoNumero = "90000000132939900";
		String nossoNumero = String.format("9%012d9900", sequencial);
		
		logger.info("Nosso numero: " +nossoNumero);

		long numero = Long.parseLong(nossoNumero);
		
		nossoNumero = nossoNumero + String.valueOf(calculaDVNossoNumero(numero));
		
		return nossoNumero;
	}
	
}
