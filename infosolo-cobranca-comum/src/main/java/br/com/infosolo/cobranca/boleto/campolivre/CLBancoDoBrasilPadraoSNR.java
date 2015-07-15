package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;

public class CLBancoDoBrasilPadraoSNR extends AbstractCLBancoDoBrasil {
	private static final long serialVersionUID = -3301260856323349739L;

	private static final Integer TAMANHO_CAMPOS = 2;
	
	CLBancoDoBrasilPadraoSNR(Arrecadacao arrecadacao) {
		super(TAMANHO_CAMPOS, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
	
		// Número da guia (nosso número)
		// Tamanho: 18
		this.add(new Campo<String>(arrecadacao.getNossoNumero(), 18, Preenchedor.ZERO_ESQUERDA));

		// Fixo 000
		// Tamanho: 3
		this.add(new Campo<Integer>(0, 3, Preenchedor.ZERO_ESQUERDA));
	}
}
