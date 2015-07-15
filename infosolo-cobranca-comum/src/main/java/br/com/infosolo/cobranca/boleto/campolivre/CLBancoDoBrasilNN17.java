package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;

/**
 * 
 * O campo livre do Banco do Brasil com o nosso número de 17 dígitos deve seguir
 * esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th>Posição </th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </thead> </tr>
 * <tr>
 * <td>20-25</td>
 * <td>6</td>
 * <td>9(6) </td>
 * <td>Conta do cedente (sem dígito)</td>
 * <td>Convênio (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>26-42</td>
 * <td>17</td>
 * <td>9(17) </td>
 * <td>Nosso Número (sem dígito)</td>
 * <td>Nosso Número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>43-44</td>
 * <td>2</td>
 * <td>9(2) </td>
 * <td>Fixo 21 (serviço)</td>
 * <td>Fixo 21 (serviço)</td>
 * </tr>
 * </table>
 * 
 */
class CLBancoDoBrasilNN17 extends AbstractCLBancoDoBrasil{
	private static final long serialVersionUID = 3035422743112225831L;

	/**
	 * Codigo obrigatorio para indicar a utilização do nosso número de 17 posições livres.
	 * Se o código "21" não for informado, o Sistema de Cobrança interpretará o Código de Barras/
	 * Linha Digitável como "nosso -número" de 11 posições. 
	 */
	private static final int SERVICO = 21;
	
	/**
	 * Quantidade de campos
	 */
	private static final Integer TAMANHO_CAMPOS = 3;
	
	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco do Brasil
	 *   que tenha o nosso número de tamanho 17.  
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilNN17(Boleto titulo) {
		super(TAMANHO_CAMPOS, TAMANHO_CAMPO_LIVRE);
		
		ContaBancaria conta = titulo.getCedente().getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();
		
		this.add(new Campo<Integer>(new Integer(conta.getConta()), 6, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<String>(nossoNumero, 17, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<Integer>(SERVICO, 2));
		
	}
	
}
