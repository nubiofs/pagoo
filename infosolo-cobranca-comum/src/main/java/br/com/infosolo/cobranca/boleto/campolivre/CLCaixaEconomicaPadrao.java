package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;

public class CLCaixaEconomicaPadrao extends AbstractCLCaixaEconomica {
	private static final long serialVersionUID = 8158964691183598578L;
	
	/**
	 * 
	 */
	private static final Integer TAMANHO_CAMPOS = 2;
	
	/**
	 * <p>
	 *   Dada uma arrecadacão, cria um campo livre para o padrão do Banco do Brasil
	 *   para todos os tipos de segmento (exceto o 9).  
	 * </p>
	 * @param arrecadacao título com as informações para geração do campo livre
	 */
	CLCaixaEconomicaPadrao(Arrecadacao arrecadacao) {
		super(TAMANHO_CAMPOS, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		
		// Número da guia (nosso número)
		// Tamanho: 18
		this.add(new Campo<String>(arrecadacao.getNossoNumero(), 18, Preenchedor.ZERO_ESQUERDA));
		
		// Fixo 000
		// Tamanho: 3
		this.add(new Campo<Integer>(0, 3, Preenchedor.ZERO_ESQUERDA));
		
	}
	
}
