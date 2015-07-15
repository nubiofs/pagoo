package br.com.infosolo.cobranca.boleto.campolivre;

/**
 * 
 * <p>
 * Exceção indicadora de não existência de um referido banco ou problemas com
 * dados de um banco.
 * </p>
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author Misael Barreto 
 * @author Rômulo Augusto
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class NaoSuportadoBancoExecao extends CampoLivreExecao {
	private static final long serialVersionUID = -1534785931775498852L;
	
	private static String msg = "Banco não suportado por não haver " +
								"implementações de Campo Livre para " +
								"o mesmo.";
		
	public NaoSuportadoBancoExecao() {
		super(msg);
	}
	
	private NaoSuportadoBancoExecao(String message, Throwable cause) {
		super(message, cause);
	}
	
	private NaoSuportadoBancoExecao(String message) {
		super(message);
	}
	
	private NaoSuportadoBancoExecao(Throwable cause) {
		super(msg, cause);
	}
	
}
