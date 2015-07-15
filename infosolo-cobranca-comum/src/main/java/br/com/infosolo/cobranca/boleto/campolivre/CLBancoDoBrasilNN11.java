package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;

/**
 * 
 * O campo livre do Banco do Brasil com o nosso número de 11 dígitos deve seguir
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
 * <td>20-30</td>
 * <td>11</td>
 * <td>9(11) </td>
 * <td>Nosso número (sem dígito)</td>
 * <td>Nosso número (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>31-34</td>
 * <td>4</td>
 * <td>9(4) </td>
 * <td>Código da agência (sem dígito)</td>
 * <td>Código da Agência (sem dígito)</td>
 * </tr>
 * <tr>
 * <td>35-42</td>
 * <td>8</td>
 * <td>9(8) </td>
 * <td>Código da conta (sem dígito)</td>
 * <td>Convênio (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >43-44</td>
 * <td >2</td>
 * <td >9(2) </td>
 * <td >Carteira</td>
 * <td >Carteira</td>
 * </tr>
 * </table>
 * 
 */
class CLBancoDoBrasilNN11 extends AbstractCLBancoDoBrasil {
	private static final long serialVersionUID = -4859699102593834115L;
	
	/**
	 * Quantidade de campos
	 */
	private static final Integer TAMANHO_CAMPOS = 4;
	
	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco do Brasil
	 *   que tenha o nosso número de tamanho 11.  
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilNN11(Boleto titulo) {
		super(TAMANHO_CAMPOS, TAMANHO_CAMPO_LIVRE);
		
		ContaBancaria conta = titulo.getCedente().getContaBancaria();
		String nossoNumero = titulo.getNossoNumero();
		
		this.add(new Campo<String>(nossoNumero, 11, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<Integer>(new Integer(conta.getAgencia()), 4, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<Integer>(new Integer(conta.getConta()), 8, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<Integer>(new Integer(titulo.getCarteira()), 2, Preenchedor.ZERO_ESQUERDA));
	}

}
