package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;


/**
 * 
 * O campo livre do Banco do Brasil com o nosso número de 10 dígitos deve seguir
 * esta forma:
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="60%" id="campolivre">
 * <tr> <thead>
 * <th >Posição </th>
 * <th >Tamanho</th>
 * <th >Picture</th>
 * <th>Conteúdo (terminologia padrão)</th>
 * <th>Conteúdo (terminologia do banco)</th>
 * </thead> </tr>
 * <tr>
 * <td >20-25</td>
 * <td >6</td>
 * <td >9(6) </td>
 * <td >ZEROS</td>
 * <td >ZEROS</td>
 * </tr>
 * <tr>
 * <td >26-32</td>
 * <td >7</td>
 * <td >9(7) </td>
 * <td >Conta do cedente (sem dígito)</td>
 * <td >Convênio (sem dígito)</td>
 * </tr>
 * <tr>
 * <td >33-42</td>
 * <td >10</td>
 * <td >9(10) </td>
 * <td >Nosso Número</td>
 * <td >Nosso Número</td>
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
class CLBancoDoBrasilNN10 extends AbstractCLBancoDoBrasil { 
	private static final long serialVersionUID = -7675528811239346517L;

	/**
	 * Quantidade de campos
	 */
	private static final Integer TAMANHO_CAMPOS = 4;

	/**
	 * <p>
	 *   Dado um título, cria um campo livre para o padrão do Banco do Brasil
	 *   que tenha o nosso número de tamanho 10.  
	 * </p>
	 * @param titulo título com as informações para geração do campo livre
	 */
	CLBancoDoBrasilNN10(Boleto titulo) {
		super(TAMANHO_CAMPOS, TAMANHO_CAMPO_LIVRE);
		
		ContaBancaria conta = titulo.getCedente().getContaBancaria();
		
		String nossoNumero = titulo.getNossoNumero();
		
		this.add(new Campo<String>("", 6, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<Integer>(new Integer(conta.getConta()), 7, Preenchedor.ZERO_ESQUERDA));
		
		this.add(new Campo<String>(nossoNumero, 10, Preenchedor.ZERO_ESQUERDA));	
		
		this.add(new Campo<Integer>(new Integer(titulo.getCarteira()), 2, Preenchedor.ZERO_ESQUERDA));
		
	}

}
