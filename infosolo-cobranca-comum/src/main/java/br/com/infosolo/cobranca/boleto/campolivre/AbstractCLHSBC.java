package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;

/**
 * 
 * Gerador de Campo Livre para o Banco HSBC.
 * 
 * Implementações: CNR - Cobrança Não Regitrada
 * 				   COB - Cobrança Registrada
 * 
 * @author <a href="mailto:leandro.lima@infosolo.com.br">Leandro Lima</a>
 */
abstract class AbstractCLHSBC extends AbstractCampoLivre {
	private static final long serialVersionUID = 3179450500491723317L;

	protected AbstractCLHSBC(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);

	}

	static CampoLivre create(Boleto boleto) {
		CampoLivre campoLivre = new CLHSBCCobranca(boleto);
		return campoLivre;
	}
}
