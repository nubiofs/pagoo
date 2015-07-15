package br.com.infosolo.cobranca.boleto;

/**
 * @author misael
 *
 */
public class Contribuinte extends EntidadeDeCobranca {
	private static final long serialVersionUID = 3267851061149256619L;

	/**
	 * 
	 */
	public Contribuinte() {
		super();
	}
	
	/**
	 * @param nome
	 * @param cpf
	 */
	public Contribuinte(String nome, String cpf) {
		super(nome, cpf);
	}

	/**
	 * @param nome
	 */
	public Contribuinte(String nome) {
		super(nome);
	}
	
}
