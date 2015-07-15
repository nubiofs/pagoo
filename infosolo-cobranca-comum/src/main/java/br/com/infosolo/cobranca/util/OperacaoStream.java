package br.com.infosolo.cobranca.util;

import java.io.Serializable;

/**
 * 
 * <p>
 * Interface generica e representativa de coisas que necessitam de operacoes de
 * fluxo de escrita e leitura.
 * </p>
 * 
 */
public interface OperacaoStream<G> extends Serializable {

	/**
	 * <p>
	 * Escreve o tipo infomado.
	 * </p>
	 * 
	 * @return Um valor no mesmo tipo do tipo parametrizado
	 * @since 0.2
	 */
	public abstract G write();

	/**
	 * <p>
	 * Le o tipo informado.
	 * </p>
	 * 
	 * @param g
	 *            valor a ser lido
	 * @since 0.2
	 */
	public abstract void read(G g);

}
