package br.com.infosolo.cobranca.boleto.campolivre;

import java.util.Calendar;
import java.util.Date;

import br.com.infosolo.cobranca.boleto.ParametrosBancariosMap;
import br.com.infosolo.cobranca.boleto.TipoIdentificadorCNR;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.enumeracao.TipoCobranca;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;

/**
 * Campo livre do Banco HSBC para cobranças registradas (COB) e não registradas (CNR).
 * 
 * @author Leandro
 *
 */
public class CLHSBCCobranca extends AbstractCLHSBC {
	private static final long serialVersionUID = -1253549781074159862L;

	/**
	 * Quantidade de campos de Cobrança CNR
	 */
	private static final Integer TAMANHO_CAMPOS_CNR = 4;

	/**
	 * Quantidade de campos de Cobrança COB
	 */
	private static final Integer TAMANHO_CAMPOS_COB = 5;

	public CLHSBCCobranca(Boleto boleto) {
		super(TAMANHO_CAMPOS_CNR, TAMANHO_CAMPO_LIVRE);
		
		checkExistsParametrosBancarios(boleto);
		
		if (boleto.getCedente().getTipoCobranca() == TipoCobranca.NAO_REGISTRADA) {
			criarCobrancaNaoRegistrada(boleto);
		}
		else {
			this.setFieldsLength(TAMANHO_CAMPOS_COB);
			criarCobrancaRegistrada(boleto);
		}
		
	}
	
	/**
	 * 
	 * 	O campo livre do HSBC, para cobrança não registrada(CNR), deve seguir esta forma:
	 * 
	 * 	<table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
	 * 	collapse" bordercolor="#111111" width="60%" id="campolivre">
	 * 		<tr>
	 * 			<thead>
	 *				<th>Posição </th>
	 * 				<th>Tamanho</th>
	 * 				<th>Picture</th>
	 * 				<th>Conteúdo (terminologia padrão)</th>
	 * 				<th>Conteúdo (terminologia do banco)</th>
	 * 			</thead>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>20-26</td>
	 * 			<td>7</td>
	 * 			<td>9(7) </td>
	 * 			<td>Conta do cedente (sem dígito)</td>
	 * 			<td>Código do cedente</td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>27-39</td>
	 * 			<td>13</td>
	 * 			<td>9(13) </td>
	 * 			<td>Nosso número (sem dígito)</td>
	 * 			<td>
	 * 				Número bancário - Código do documento, sem os dígitos
	 * 				verificadores e tipo identificador.
	 * 			</td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>40-43</td>
	 * 			<td>4</td>
	 * 			<td>9(4) </td>
	 * 			<td>Fator de vencimento</td>
	 * 			<td>Data do vencimento no formato juliano</td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>44-44</td>
	 * 			<td>1</td>
	 * 			<td>9(1) </td>
	 * 			<td>2 FIXO</td>
	 * 			<td>Código do Aplicativo CNR = 2</td>
	 * 		</tr>
	 * </table>
	 * 
	 */
	private void criarCobrancaNaoRegistrada(Boleto titulo) {
		checkExistsParametroTipoIdentificadorCNR(titulo.getParametrosBancarios());

		TipoIdentificadorCNR tipoIdentificadorCNR = titulo
				.getParametrosBancarios().getValor(TipoIdentificadorCNR.class.getName());

		ContaBancaria conta = titulo.getCedente().getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();

		// Conta do cedente (sem dígito)
		this.add(new Campo<Integer>(Integer.valueOf(conta.getConta()), 7, Preenchedor.ZERO_ESQUERDA));

		// Nosso número (sem dígito)
		this.add(new Campo<String>(nossoNumero, 13, Preenchedor.ZERO_ESQUERDA));

		this.add(new Campo<String>(getDataVencimentoFormatoJuliano(
				tipoIdentificadorCNR, titulo.getDataVencimento()), 4, Preenchedor.ZERO_ESQUERDA));

		// 2 FIXO (Código do Aplicativo CNR - Cob. Não Registrada)
		this.add(new Campo<Integer>(2, 1));

	}

	/**
	 * Cria campo livre para a Cobranca Registrada (COB)
	 * 
	 * @param titulo
	 */
	private void criarCobrancaRegistrada(Boleto titulo) {
		ContaBancaria conta = titulo.getCedente().getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();

		// Nosso número 
		this.add(new Campo<String>(nossoNumero, 11, Preenchedor.ZERO_ESQUERDA));

		// Agencia do cedente
		this.add(new Campo<Integer>(Integer.valueOf(conta.getAgencia()), 4, Preenchedor.ZERO_ESQUERDA));

		// Conta do cedente
		this.add(new Campo<Integer>(Integer.valueOf(conta.getConta()), 7, Preenchedor.ZERO_ESQUERDA));

		// FIXO: Código da carteira = "00"
		this.add(new Campo<Integer>(0, 2, Preenchedor.ZERO_ESQUERDA));

		// FIXO: Código do Aplicativo COB - Cobrança Registrada
		this.add(new Campo<Integer>(1, 1));
	}

	/**
	 * 
	 * @param tipoIdentificadorCNR
	 * @param vencimento
	 * @return
	 */
	private String getDataVencimentoFormatoJuliano(
			TipoIdentificadorCNR tipoIdentificadorCNR, Date vencimento) {

		switch (tipoIdentificadorCNR) {
			case SEM_VENCIMENTO:
				return "0000";
	
			case COM_VENCIMENTO:
				return getVencimentoFormatoJuliano(vencimento);
	
			default:
				throw new IllegalStateException("Tipo de identificador CNR desconhecido!");
		}
	}

	/**
	 * 
	 * @param vencimento
	 * @return
	 */
	private String getVencimentoFormatoJuliano(Date vencimento) {
		Calendar c = Calendar.getInstance();
		c.setTime(vencimento);

		return String.valueOf(c.get(Calendar.DAY_OF_YEAR)) + String.valueOf(c.get(Calendar.YEAR) % 10);
	}

	/**
	 * 
	 * @param titulo
	 */
	private void checkExistsParametrosBancarios(Boleto titulo) {
		if (titulo.getParametrosBancarios() != null && titulo.getParametrosBancarios().isVazio()) {

			throw new CampoLivreExecao("Parâmetros bancários nulos em \"Titulo.parametrosBancarios\". O parâmetro bancário de nome e tipo [ "
							+ TipoIdentificadorCNR.class.getName() + " ] deve ser fornecido para este caso.");
		}
	}

	/**
	 * 
	 * @param parametros
	 */
	private void checkExistsParametroTipoIdentificadorCNR(
			ParametrosBancariosMap parametros) {

		if (parametros != null) {
			TipoIdentificadorCNR tipoIdentificadorCNR = parametros.getValor(TipoIdentificadorCNR.class.getName());
	
			if (tipoIdentificadorCNR == null) {
				throw new CampoLivreExecao("Parâmetro bancário [" + TipoIdentificadorCNR.class.getName()
						+ " ] não encontrado em \"Titulo.parametrosBancarios\". O nome do parâmetro deve ser o \"qualify name\" da classe.");
			}
		}
	}
}
