package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;
import br.com.infosolo.comum.util.DataUtil;

class CLBancoDoBrasilSegmento9 extends AbstractCLBancoDoBrasil { 
	private static final long serialVersionUID = 5437349135380008187L;

	/**
	 * 
	 */
	private static final Integer TAMANHO_CAMPOS = 4;

	/**
	 * <p>
	 *   Dada uma arrecadacão, cria um campo livre para o padrão do Banco do Brasil
	 *   para o tipo de segmento 9.  
	 * </p>
	 * @param arrecadacao título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilSegmento9(Arrecadacao arrecadacao) {
		super(TAMANHO_CAMPOS, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		
		// Dois primeiros dígitos do CNPJ do órgão recebedor.
		// Tamanho: 2
		String digitos11Com12DoCNPJ = arrecadacao.getOrgaoRecebedor().getCNPJ().substring(10,12);
		this.add(new Campo<String>(digitos11Com12DoCNPJ, 2, Preenchedor.ZERO_ESQUERDA));
		
		// Código do convênio.
		// Tamanho: 6
		this.add(new Campo<Integer>(arrecadacao.getConvenio().getNumero(), 6, Preenchedor.ZERO_ESQUERDA));
		
		// Data de vencimento no formato YYYYMMDD.
		// Tamanho: 8	
		String dataFormatadaYYYYMMDD = DataUtil.formatarData(arrecadacao.getDataDoVencimento(), DataUtil.FORMATO_DATA_AAAA_MM_DD);
		this.add(new Campo<String>(dataFormatadaYYYYMMDD, 8));	

		// Número da guia (nosso número)
		// Tamanho: 9
		this.add(new Campo<String>(arrecadacao.getNossoNumero(), 9, Preenchedor.ZERO_ESQUERDA));
	}

}
