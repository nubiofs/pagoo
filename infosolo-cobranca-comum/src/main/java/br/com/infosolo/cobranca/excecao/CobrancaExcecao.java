package br.com.infosolo.cobranca.excecao;

public class CobrancaExcecao extends RuntimeException {
	private static final long serialVersionUID = 7624136656012911066L;

	/**
	 * 
	 * @see java.lang.RuntimeException#RuntimeException()
	 * 
	 * @since 0.2
	 */
	public CobrancaExcecao() {
		super();
	}

	/**
     * Construtor para excecao do tipo Exception
     * @param e
     */
    public CobrancaExcecao(Exception e){
        super(e);
    }
    
    /**
     * Construtor para excecao do tipo Throwable
     * @param e
     */
    public CobrancaExcecao(Throwable th){
        super(th);
    }    
    
    /**
     * Constructor para excecao do tipo String
     * 
     * @param msg the error message associated with the exception
     */
    public CobrancaExcecao(String msg) {
        super(msg);
    }

    /**
     * Constructor com mensagem de erro e causa inicial.
     * 
     * @param msg the error message associated with the exception
     * @param cause the root cause of the exception
     */
    public CobrancaExcecao(String msg, Throwable cause) {
        super(msg, cause);
    }

}
