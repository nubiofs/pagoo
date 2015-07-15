package br.com.infosolo.cobranca.boleto.guia;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import br.com.infosolo.cobranca.boleto.Modulo;
import br.com.infosolo.cobranca.boleto.TipoSeguimento;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivre;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.util.AbstractLinhaDeCampos;
import br.com.infosolo.cobranca.util.BancoUtil;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;

/**
 * 
 * É um número único para cada Guia composto dos seguintes campos:
 * <br/><br/>
 * 
 * <table border="1" cellpadding="1" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="85%" id="composição"> 
 * <thead>
 * <tr>
 * 		<th>Posição</th>
 * 		<th>Tamanho</th>
 * 		<th>Conteúdo</th>
 * 		<th>Descricao</th>
 * </tr>
 * </thead>
 * <tr>
 * 		<td>01-01</td>
 * 		<td>1</td>
 * 		<td>Identificação do Produto</td>
 * 		<td>Constante "8" para identificar arrecadação</td>
 * </tr>
 * <tr>
 * 		<td>02-02 </td>
 * 		<td>1 </td>
 * 		<td>Identificação do Segmento</td>
 * 		<td>
 * 			Identificará o segmento e a forma de identificação da Empresa/Órgão: <br/> 
 * 			1. Prefeituras; <br/> 
 * 			2. Saneamento;  <br/> 
 * 			3. Energia Elétrica e Gás; <br/>  
 * 			4. Telecomunicações;  <br/> 
 * 			5. Órgãos Governamentais; <br/>  
 * 			6. Carnes e Assemelhados ou demais  <br/>  
 * 			Empresas / Órgãos que serão identificadas através do CNPJ. <br/>  
 * 			7. Multas de trânsito  <br/> 
 * 			9. Uso exclusivo do banco  <br/> 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>03-03 </td>
 * 		<td>1</td>
 * 		<td>Identificação do valor real ou referência</td>
 * 		<td>
 * 
 * 			Este campo será: <br/>  <br/>
 * 			<strong>
 * 			“6”- Valor   a   ser  cobrado   efetivamente   em   reais
 * 			</strong>  <br/>
 * 			 com  dígito verificador calculado pelo módulo 10  na quarta  
 * 			posição do Código de  Barras e  valor  com    11 posições (versão 
 * 			2 e posteriores) sem qualquer alteração; <br/> <br/>
 * 			 
 * 			<strong>
 * 			“7”-  Quantidade   de  moeda 
 * 			</strong> <br/>  
 * 			Zeros – somente na impossibilidade de utilizar o valor;  <br/>
 * 			Valor a   ser  reajustado   por  um índice com  dígito verificador 
 * 			calculado pelo módulo 10   na  quarta 
 * 			posição do Código de Barras e valor com 11 posições  (versão 2 e 
 * 			posteriores).  <br/> <br/>
 * 			 
 * 			<strong>
 * 			“8” – Valor a ser cobrado efetivamente em reais
 * 			</strong><br/>
 * 			com  dígito verificador  calculado pelo módulo 11  na quarta  
 * 			posição do Código de  Barras e  valor  com 11 posições (versão 2 
 * 			e posteriores) sem qualquer alteração.  <br/><br/>
 * 			 
 * 			<strong>
 * 			“9” – Quantidade de moeda 
 *			</strong> <br/>
 * 			Zeros – somente na impossibilidade de utilizar o valor; 
 * 			Valor a   ser  reajustado   por  um índice 
 * 			 
 * 			com   dígito  .verificador calculado pelo módulo 11   na  quarta 
 * 			posição do Código de Barras e valor com 11 posições  (versão 2 e 
 * 			posteriores).  <br/>
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>04-04</td>
 * 		<td>1</td>
 * 		<td>Dígito verificador geral (módulo 10 ou 11)</td>
 * 		<td>   Dígito de auto conferência dos dados contidos  no Código de Barras.</td>
 * </tr>
 * <tr>
 * 		<td>05–15</td>
 * 		<td>11</td>
 * 		<td>Valor</td>
 * 		<td>
 * 			Se o campo “03 – Código de Moeda” indicar valor efetivo, este campo 
 * 			deverá conter o valor a ser cobrado. <br/><br/>

 * 			Se o campo “03 - Código de Moeda” indicado valor de referência, neste 
 * 			campo poderá conter uma quantidade de moeda, zeros, ou um valor a ser 
 * 			reajustado por um índice, etc... 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>16–19 <br/><br/> ou <br/><br/> 16-23</td>
 * 		<td>4 <br/><br/> ou <br/><br/> 8</td>
 * 		<td>
 * 				Identificação da Empresa/Órgão <br/><br/>
 * 				<CENTER>
 * 				Codigo Banco Febraban (4)  <br/> ou <br/>CNPJ Orgão Recebedor(8)
 * 				</CENTER>
 * 		</td>
 * 		<td>
 * 			O campo identificação da Empresa/Órgão terá uma codificação especial 
 * 			para cada segmento. <br/><br/>
 * 		 
 * 			Será um código de quatro posições atribuído e controlado pela Febraban (posições 16 a 19), 
 * 			ou as primeiras oito posições do cadastro geral de contribuintes do 
 * 			Ministério da Fazenda - CNPJ (posições 16 a 23). <br/><br/>
 * 		 
 * 			É através desta informação que o banco identificará a quem repassar as 
 * 			informações e o crédito. <br/><br/>  
 * 		 
 * 			Se for utilizado o CNPJ para identificar a Empresa/Órgão (posições 16 a 23), haverá uma 
 * 			redução no seu campo livre que passará a conter 21 posições (posições 24 a 44). <br/><br/> 
 * 		 
 * 			No caso de utilização do Segmento 9, este campo deverá conter o código 
 * 			de compensação do mesmo, com quatro dígitos (posições 16 a 29). Neste
 * 			caso o campo livre permanece com o tamanho padrão (posições 20 a 44)<br/><br/> 
 * 		 
 * 			Cada banco definirá a forma de identificação da empresa a partir da 1ª 
 * 			posição do campo livre (20 ou 24, de acordo com o que já foi explanado). 
 * 		</td>
 * </tr>
 * <tr>
 * 		<td>20–44 <br/><br/> ou <br/><br/> 24-44</td>
 * 		<td>25 <br/><br/> ou <br/><br/> 21</td>
 * 		<td> Campo livre de utilização da Empresa/Órgão</td>
 * 		<td>
 * 			Este campo é de uso exclusivo da Empresa/Órgão e será devolvido 
 * 			inalterado. <br/><br/>
 * 			 
 * 			Se existir data de vencimento no campo livre, ela deverá vir em primeiro 
 * 			lugar e em formato AAAAMMDD. 
 * 		</td>
 * </tr>
 * </table>
 * 
 * 
 * @author Misael Barreto 
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public final class CodigoDeBarras extends AbstractLinhaDeCampos {
	private static final long serialVersionUID = -6280859254008661464L;

	
	private static Logger logger = new Logger(AbstractLinhaDeCampos.class);
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 7;
	
	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 44;

	/**
	 * Tamanho do codigo sem o digito verificador (Digito de Autoconferencia)
	 */
	private static final int TAMANHO_SEM_DV = 43;
	
	/**
	 *  Identificação do Produto. 
	 */
	private Campo<Integer> produto;
	
	/**
	 *  Identificação do Segmento.
	 */
	private Campo<Integer> segmento;
	
	/**
	 * Identificação do valor real ou referência.
	 */
	private Campo<Integer> valorReferencia;
	
	/**
	 * Dígito verificador geral (módulo 10 ou 11) 
	 * Mecanismo de autenticação usado no composição de barras.
	 * 
	 * @see br.com.nordestefomento.jrimum.vallia.digitoverificador.GuiaCodigoDeBarrasDV
	 */
	private Campo<Integer> digitoVerificadorGeral;
	
	/**
	 *  Valor
	 */
	private Campo<BigDecimal> valor;
	

	/**
	 *  Identificação da Empresa/Órgão.
	 *  Seu tamanho pode varias de acordo com a forma com a Empresa/Órgão Recebedor
	 *  for identificado.
	 *  
	 *   @see br.com.nordestefomento.jrimum.bopepo.guia.CodigoDeBarras
	 */
	private Campo<String> orgao;
	
	
	/**
	 * @see br.com.infosolo.cobranca.boleto.campolivre.CampoLivreGuia.com.nordestefomento.jrimum.bopepo.campolivre.boleto.guia.CampoLivre
	 * Seu tamanho pode varias de acordo com a forma com a Empresa/Órgão Recebedor
	 * for identificado.
	 * 
	 *  @see br.com.nordestefomento.jrimum.bopepo.guia.CodigoDeBarras
	 */
	private Campo<String> campoLivre;
	
	
	private Modulo moduloParaCalculoDV;
	
	/**
	 * <p>
	 * Cria um Código de Barras a partir de uma arrecadação e campo livre passados.
	 * </p>
	 * 
	 * @param arrecadacao
	 * @param campoLivre
	 * 
	 * @see CampoLivreGuia
	 */
	CodigoDeBarras(Arrecadacao arrecadacao, CampoLivre campoLivre) {
		super(FIELDS_LENGTH ,STRING_LENGTH);
		
		logger.debug("Instanciando o CodigoDeBarras");
			
		logger.debug("titulo instance : "+arrecadacao);
		logger.debug("campoLivre instance : "+campoLivre);


		// Configurando os campos.
		produto = new Campo<Integer>(0, 1, Preenchedor.ZERO_ESQUERDA);
		segmento = new Campo<Integer>(0, 1, Preenchedor.ZERO_ESQUERDA);
		valorReferencia = new Campo<Integer>(0, 1, Preenchedor.ZERO_ESQUERDA);
		digitoVerificadorGeral = new Campo<Integer>(0, 1, Preenchedor.ZERO_ESQUERDA);
		valor = new Campo<BigDecimal>(new BigDecimal(0), 11, Preenchedor.ZERO_ESQUERDA);
		
		// Configurando o tamanho dos campos orgão e campo livre. 
		// Se o tipo de seguimento for USO_EXCLUSIVO_BANCO (9), a identificação do órgão
		// será feita com o código do banco, com tamanho 4. Caso contrário, a identificaão
		// será feita com os 8 primeiros dígitos do CNPJ do órgão recebedor. 
		if (arrecadacao.getOrgaoRecebedor().getTipoSeguimento() == TipoSeguimento.USO_EXCLUSIVO_BANCO) {
			this.orgao = new Campo<String>("0", 4, Preenchedor.ZERO_ESQUERDA);
			this.campoLivre = new Campo<String>(StringUtils.EMPTY, 25);
		}
		else {
			this.orgao = new Campo<String>("0", 8, Preenchedor.ZERO_ESQUERDA);
			this.campoLivre = new Campo<String>(StringUtils.EMPTY, 21);
		}		
			 
		
		// Adicionando os campos no código de barras, devidamente configurados.
		add(this.produto);
		add(this.segmento);
		add(this.valorReferencia);
		add(this.digitoVerificadorGeral);
		add(this.valor);
		add(this.orgao);
		add(this.campoLivre);
	
		
		// Informando o valor de cada campo.
		this.produto.setValue(Arrecadacao.TIPO_PRODUTO.getCodigo());
		
		//this.segmento.setValue(arrecadacao.getOrgaoRecebedor().getTipoSeguimento().getCodigo());
		this.segmento.setValue(TipoSeguimento.USO_EXCLUSIVO_BANCO.getCodigo());
		
		this.valorReferencia.setValue(arrecadacao.getTipoValorReferencia().getCodigo());
		
		//this.valor.setValue(new BigDecimal(arrecadacao.getValorDocumento()).movePointRight(2));
		this.valor.setValue(new BigDecimal(arrecadacao.getValorDocumento()));
		
		if (arrecadacao.getOrgaoRecebedor().getTipoSeguimento() == null) {
			String convenio = String.format("%012d", arrecadacao.getConvenio().getNumero());
			if (arrecadacao.getConvenio().getBanco().getCodigo() == Banco.BANCO_BRASIL.getCodigo()) {
				this.orgao.setValue(convenio.substring(0, 8));
			}
			else {
				this.orgao.setValue(convenio.substring(convenio.length() - 8));
			}
		}
		else if (arrecadacao.getOrgaoRecebedor().getTipoSeguimento() == TipoSeguimento.USO_EXCLUSIVO_BANCO) {
			// Pegando o código do banco como inteiro. O filler deste campo vai se encarregar de deixar o código
			// com quatro dígitos, com zeros à esquerda.
			// Neste caso, a identificação do órgão recebedor será feita através
			// do código do banco(presente no campo órgão) mais o código do 
			// convênio (presente no campo livre) entre o banco e o órgão recebedor.
			this.orgao.setValue(TextoUtil.retiraSimbolos(BancoUtil.retornarCodigoBancoBACEN(arrecadacao.getConvenio().getBanco())));
		}
		else {
			// Caso contrário, a identificação será feita através dos 8 primeiros
			// dígitos do CNPJ do órgão recebedor, presente no campo órgão.
			String cnpjDoOrgaoRecebedorFormatadoSemPontucao = arrecadacao.getOrgaoRecebedor().getCNPJ();
			this.orgao.setValue(cnpjDoOrgaoRecebedorFormatadoSemPontucao.substring(0, 8));
		}
		
		// Escrevendo os dados do campo livre informado como parâmetro deste
		// método.
		this.campoLivre.setValue(campoLivre.write());
		
		//TODO: TESTE
//		this.valor.setValue(new BigDecimal("1.00"));
//		this.orgao.setValue("00010110");
//		this.campoLivre.setValue("782100000014476903000");
		
		// Obtendo qual deverá ser o módulo utilizado para o cálculo do dígito
		// verificador geral do código de barras da guia. Em seguida é realizado
		// o cálculo do mesmo.
		this.moduloParaCalculoDV = arrecadacao.getTipoValorReferencia().getModulo();
		this.calculateAndSetDigitoVerificadorGeral(this.moduloParaCalculoDV);
		
		logger.debug("codigoDeBarra instanciado : " + this);
	}

	private void calculateAndSetDigitoVerificadorGeral(Modulo modulo) {
		
		logger.debug("Calculando Digito Verificador Geral do Código de Barras...");

		// Preparando o conjunto de informações que será a base para o cálculo
		// do dígito verificador, conforme normas da FEBRABAN.
		StringBuilder baseCalculoDV = new StringBuilder(produto.write())
			.append(segmento.write())
			.append(valorReferencia.write())
			.append(valor.write())
			.append(orgao.write())
			.append(campoLivre.write());

		// Realizando o cálculo dígito verificador e em seguida armazenando 
		// a informação no campo "digitoVerificadorGeral".
		digitoVerificadorGeral.setValue(calcularDVCodigoBarras(baseCalculoDV.toString(), modulo));

		logger.debug("Digito Verificador Geral calculado : " + digitoVerificadorGeral.getValue());
	}

	public int calcularDVCodigoBarras(String numero, Modulo modulo) throws IllegalArgumentException {
		int dv = 0;
		int resto = 0;

		if (StringUtils.isNotBlank(numero) && StringUtils.isNumeric(numero)
				&& (numero.length() == TAMANHO_SEM_DV)) {

			// Realizando o cálculo do dígito verificador.
			resto = modulo.calcule(numero);
			
			if (modulo.getMod() == TipoDeModulo.MODULO11.valor()) {
				// Seguindo as especificações da FEBRABAN, caso o resto seja
				// (0), (1) ou (10), será atribuído (1) ao digito verificador.			
				if ((resto == 0) || (resto == 1) || (resto == 10))
					dv = 1;
				// Caso contrário, dv = 11 - resto.
				else
					dv = modulo.valor() - resto;
			}
			
			else if (modulo.getMod() == TipoDeModulo.MODULO10.valor()) {
				// Seguindo as especificações da FEBRABAN, caso o resto seja
				// (0) ou (10), será atribuido o valor zero.
				if (  (resto == 0) || (resto == 10)  )
					dv = 0;
				// Caso contrário, dv = 10 - resto.
				else
					dv = modulo.valor() - resto;
			}
			
		} else {
			throw new IllegalArgumentException("O código de barras " + "[ "
					+ numero + " ] deve conter apenas números e "
					+ TAMANHO_SEM_DV + " caracteres.");
		}

		return dv;
	}
	
	/**
	 * @return the produto
	 */
	public Campo<Integer> getProduto() {
		return produto;
	}

	/**
	 * @return the segmento
	 */
	public Campo<Integer> getSegmento() {
		return segmento;
	}



	/**
	 * @return the valorReferencia
	 */
	public Campo<Integer> getValorReferencia() {
		return valorReferencia;
	}



	/**
	 * @return the digitoVerificadorGeral
	 */
	public Campo<Integer> getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}



	/**
	 * @return the valor
	 */
	public Campo<BigDecimal> getValor() {
		return valor;
	}



	/**
	 * @return the orgao
	 */
	public Campo<String> getOrgao() {
		return orgao;
	}



	/**
	 * @return the campoLivre
	 */
	public Campo<String> getCampoLivre() {
		return campoLivre;
	}



	@Override
	public String toString() {
		//return TextoUtil.toString(this);
		return this.write();
	}


	/**
	 * @return the moduloParaCalculoDV
	 */
	public Modulo getModuloParaCalculoDV() {
		return moduloParaCalculoDV;
	}
	
}
