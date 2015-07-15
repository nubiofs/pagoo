package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.TipoSeguimento;
import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;

public class AbstractCLCaixaEconomica extends AbstractCampoLivre {
	private static final long serialVersionUID = 997516205987019168L;

	protected AbstractCLCaixaEconomica(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}
	
	protected AbstractCLCaixaEconomica(Integer fieldsLength, TipoSeguimento tipoSeguimento) {
		super(fieldsLength, tipoSeguimento);
	}

	public static CampoLivre create(Arrecadacao arrecadacao) throws NaoSuportadoCampoLivreExcecao {			
		CampoLivre campoLivre = null;
		
		campoLivre = new CLCaixaEconomicaPadrao(arrecadacao);

		return campoLivre;
	}

}
