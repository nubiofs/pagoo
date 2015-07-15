package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de retorno das arrecadações identificadas com código de barras modelo FEBRABAN 150.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheG150 extends Registro150 {

	@Descricao("Código do Convênio")
	@Posicao("2,21")
	public String codigoConvenio;

	@Descricao("Data de pagamento (AAAAMMDD)")
	@Posicao("22,29")
	public String dataPagamento;

	@Descricao("Data de crédito (AAAAMMDD)")
	@Posicao("30,37")
	public String dataCredito;
	
	@Descricao("Codigo de Barras")
	@Posicao("38,81")
	public String codigoBarras;
	
	@Descricao("Valor recebido")
	@Posicao("82,93")
	public double valorRecebido;

	@Descricao("Valor da tarifa")
	@Posicao("94,100")
	public double valorTarifa;
	
	@Descricao("Número Sequencial do Registro (NSR)")
	@Posicao("101,108")
	public long numeroSequencial;

	@Descricao("Codigo da agência arrecadadora")
	@Posicao("109,116")
	public String codigoAgencia;

	/**
	 * 1 - Guichê de Caixa com fatura/guia de arrecadação
	 * 2 - Arrecadação Eletônica com fatura/guia de arrecadação (terminais de auto-atendimento,
	 * 	   ATM, home/office banking)
	 * 3 - Internet com fatura/guia de arrecadação
	 * 4 - Outros meios com fatura/guia de arrecadação
	 * 5 - Casas lotéricas/correspondentes bancários com fatura/guia de arrecadação
	 * 6 - Telefone com fatura/guia de arrecadação
	 * a - Guiche de Caixa sem fatura/guia de arrecadação
	 * b - Arrecadação Eletrônica sem fatura/guia de arrecadação
	 * c - Internet sem fatura/guia de arrecadação
	 * d - Casas lotéricas/correspondentes bancários sem fatura/guia de arrecadação
	 * e - Telefone sem fatura/guia de arrecadação
	 * f - Outros meios sem fatura/guia de arrecadação
	 */
	@Descricao("Forma de arrecadação/captura")
	@Posicao("117,117")
	public char formaArrecadacao;

	@Descricao("Número de autenticação caixa ou codigo de transação")
	@Posicao("118,140")
	public String numeroAutenticacao;

	/**
	 * 1 - Dinheiro
	 * 2 - Cheque
	 * 3 - Não identificado
	 */
	@Descricao("Forma de Pagamento")
	@Posicao("141,141")
	public int formaPagamento;
	
	@Descricao("Reservado para futuro")
	@Posicao("142,150")
	public String reservado;
	
}
