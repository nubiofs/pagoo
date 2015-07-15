package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.util.BancoUtil;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;
import br.com.infosolo.comum.util.TextoUtil;

class CLBancoDoBrasilNovo extends AbstractCLBancoDoBrasil { 
	private static final long serialVersionUID = -8058152715495020864L;

	/**
	 * 
	 */
	private static final Integer TAMANHO_CAMPOS = 3;

	/**
	 * <p>
	 *   Dada uma arrecadacão, cria um campo livre para o padrão do Banco do Brasil
	 *   para todos os tipos de segmento (exceto o 9).  
	 * </p>
	 * @param arrecadacao título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilNovo(Arrecadacao arrecadacao) {
		super(TAMANHO_CAMPOS, arrecadacao.getOrgaoRecebedor().getTipoSeguimento());
		
		// Parte do codigo do cedente.
		// Tamanho: 4	
		String convenio = String.format("%012d", arrecadacao.getConvenio().getNumero());
		this.add(new Campo<String>(convenio.substring(8), 4));
		//this.add(new Campo<String>(TextoUtil.retiraSimbolos(BancoUtil.retornarCodigoBancoBACEN(arrecadacao.getConvenio().getBanco())), 4));
	
		// Número da guia (nosso número)
		// Tamanho: 14
		this.add(new Campo<String>(arrecadacao.getNossoNumero(), 14, Preenchedor.ZERO_ESQUERDA));

		// Fixo 000
		// Tamanho: 3
		this.add(new Campo<Integer>(0, 3, Preenchedor.ZERO_ESQUERDA));
	}

}
