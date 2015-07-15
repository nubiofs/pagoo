package br.com.infosolo.cobranca.enumeracao;


/**
 * <p>
 * Representacaoo dos tipos basicos de cobranca:<br />
 * <ul>
 * <li>Registrada (ou com registro)</li>
 * <li>Nao Registrada (ou sem registro)</li>
 * </ul>
 * </p>
 * 
 * 
 * @author <a href="mailto:leandro.lima@infosolo.com.br">Leandro Lima</a>
 * 
 */
public enum TipoCobranca {
	
	/**
	 * <p>Tipo onde os titulos emitidos só são registrados pelo banco quando são pagos.</p>
	 */
	NAO_REGISTRADA,

	/**
	 * <p>Tipo onde os titulos emitidos sao sempre registrados no banco antes de seu vencimento ou pagamento.</p>
	 */
	REGISTRADA;
	
	public static TipoCobranca findByOrdinal(int numero) {
		TipoCobranca retorno = null;
		
		TipoCobranca[] tipoArray = TipoCobranca.values();
		for (int i = 0; i < tipoArray.length; i++) {
			if (tipoArray[i].ordinal() == numero) {
				retorno = tipoArray[i];
				break;
			}
				
		}
		
		return retorno;
	}	
	
	
}
