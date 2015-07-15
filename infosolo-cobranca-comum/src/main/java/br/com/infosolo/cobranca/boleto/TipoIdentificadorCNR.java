package br.com.infosolo.cobranca.boleto;

/**
 * 
 * <p>
 * Tipo do identificador do nosso número utilizado nos títulos de boletos
 * bancários de Cobrança Não Registrada (CNR).
 * </p>
 * 
 * <p>
 * Basicamente são dois os tipos de nosso número (NN):
 * </p>
 * 
 * <ul>
 * <li>O que vincula “vencimento”, “código do cedente” e “código do documento”.
 * Enumerado como {@link #COM_VENCIMENTO } (constante 4);</li>
 * <li>O que vincula “código do cedente” e “código do documento”. Enumerado como
 * {@link #SEM_VENCIMENTO } (constante 5).</li>
 * </ul>
 * 
 * <p>
 * <strong>OBS:</strong> vale lembrar que é o tipo do identificador que
 * determina se o boleto HSBC CNR tem vencimento ou se é do tipo com vencimento
 * “À Vista” ou “Contra Apresentação”.
 * </p>
 * 
 * @author <a href=http://gilmatryx.googlepages.com/>Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */

public enum TipoIdentificadorCNR {

	/**
	 * 
	 * <p>
	 * Identificador do <strong>"tipo 4"</strong> que vincula “vencimento”,
	 * “código do cedente” e “código do documento”.
	 * </p>
	 * 
	 * @author <a href=http://gilmatryx.googlepages.com/>Gilmar P.S.L.</a>
	 * 
	 * @since 0.2
	 * 
	 * @version 0.2
	 */

	COM_VENCIMENTO {
		/**
		 * @see br.com.nordestefomento.jrimum.domkee.financeiro.banco.hsbc.TipoIdentificadorCNR#getConstante()
		 */
		@Override
		public int getConstante() {
			return 4;
		}
	},

	/**
	 * 
	 * <p>
	 * Identificador do <strong>"tipo 5"</strong> que vincula “código do
	 * cedente” e “código do documento”.
	 * </p>
	 * 
	 * @author <a href=http://gilmatryx.googlepages.com/>Gilmar P.S.L.</a>
	 * 
	 * @since 0.2
	 * 
	 * @version 0.2
	 */
	SEM_VENCIMENTO {
		/**
		 * @see br.com.nordestefomento.jrimum.domkee.financeiro.banco.hsbc.TipoIdentificadorCNR#getConstante()
		 */
		@Override
		public int getConstante() {
			return 5;
		}
	};

	/**
	 * <p>
	 * Retorna a constante numérica bancária do tipo (4 ou 5)
	 * </p>
	 * 
	 * @return constante do tipo (4 ou 5)
	 * 
	 * @since 0.2
	 */

	public abstract int getConstante();
}