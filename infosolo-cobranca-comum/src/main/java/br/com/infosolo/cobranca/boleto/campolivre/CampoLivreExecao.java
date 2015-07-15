package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.excecao.CobrancaExcecao;

public class CampoLivreExecao extends CobrancaExcecao {
	private static final long serialVersionUID = -4849492775200100621L;

	public CampoLivreExecao() {
		super();
	}

	public CampoLivreExecao(Exception e) {
		super(e);
	}
	
    public CampoLivreExecao(String msg) {
        super(msg);
    }

    public CampoLivreExecao(Throwable th){
        super(th);
    }    

    public CampoLivreExecao(String msg, Throwable cause) {
        super(msg, cause);
    }

}
