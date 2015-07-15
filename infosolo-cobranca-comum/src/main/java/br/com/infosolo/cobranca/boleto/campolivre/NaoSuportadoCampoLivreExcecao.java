package br.com.infosolo.cobranca.boleto.campolivre;

/**
 * 
 * <p>
 * Exceção indicadora de não existência de um campolivre para os dados correntes 
 * de uam guia.
 * </p>
 * 
 */
public class NaoSuportadoCampoLivreExcecao extends CampoLivreExecao {
	private static final long serialVersionUID = 6034318828391564650L;

	public NaoSuportadoCampoLivreExcecao() {
		super();
		
	}

	public NaoSuportadoCampoLivreExcecao(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NaoSuportadoCampoLivreExcecao(String message) {
		super(message);
		
	}

	public NaoSuportadoCampoLivreExcecao(Throwable cause) {
		super(cause);
		
	}

	
}
