package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;
import br.com.infosolo.comum.util.DataUtil;

class CLBancoDoBrasilPadrao extends AbstractCLBancoDoBrasil { 
	private static final long serialVersionUID = 4697152038277741060L;

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
	CLBancoDoBrasilPadrao(Arrecadacao arrecadacao) {
		super(TAMANHO_CAMPOS, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		
		// Data de vencimento no formato YYYYMMDD.
		// Tamanho: 8	
		String dataFormatadaYYYYMMDD = DataUtil.formatarData(arrecadacao.getDataDoVencimento(), DataUtil.FORMATO_DATA_AAAA_MM_DD);
		this.add(new Campo<String>(dataFormatadaYYYYMMDD, 8));	

	
		// Número da guia (nosso número)
		// Tamanho: 13
		this.add(new Campo<String>(arrecadacao.getNossoNumero(), 13, Preenchedor.ZERO_ESQUERDA));
	}

}
