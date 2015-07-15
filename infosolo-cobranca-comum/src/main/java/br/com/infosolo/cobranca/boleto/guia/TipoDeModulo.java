package br.com.infosolo.cobranca.boleto.guia;

import java.io.Serializable;

/**
 * <p>
 * Enumeração das implementações dos módulos.
 * </p>
 * 
 */
public enum TipoDeModulo implements Serializable {

	/**
	 * <p>
	 * Módulo do tipo 11.
	 * </p>
	 * 
	 */
	MODULO10 {
		public int valor() {
			return 10;
		}
	},

	/**
	 * <p>
	 * Módulo do tipo 11.
	 * </p>
	 */
	MODULO11 {
		public int valor() {
			return 11;
		}
	};

	/**
	 * <p>
	 * Retorna o valor do módulo.
	 * </p>
	 * 
	 * @return 10 ou 11
	 * 
	 * @since 0.2
	 */
	public abstract int valor();
}