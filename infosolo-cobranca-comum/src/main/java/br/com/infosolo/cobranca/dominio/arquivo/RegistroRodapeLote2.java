package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Trailer de Lote - Registro 5
 * Títulos em Carteira de Cobrança
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroRodapeLote2 extends RegistroRodapeLote {
	
	// Qtde de registros
	@Descricao("Quantidade de Registros do Lote")
	@Posicao("18,23")
	public long qtdeRegistros;
	
	// Totalizacao da Cobranca Simples
	@Descricao("Valor Total de Títulos em Cobranca Simples")
	@Posicao("24,29")
	public long qtdeTitulosCobrancaSimles;
	
	@Descricao("Quantidade de Títulos em Cobrança Simples")
	@Posicao("30,46")
	public double valorTitulosCobrancaSimples;
	
	// Totalizacao da Cobranca Vinculada
	@Descricao("Quantidade de Títulos em Cobrança Vinculada")
	@Posicao("47,52")
	public long qtdeTitulosCobrancaVinculada;
	
	@Descricao("Valor Total de Títulos em Cobranca Vinculada")
	@Posicao("53,69")
	public double valorTitulosCobrancaVinculada;
	
	// Totalizacao da Cobranca Caucionada
	@Descricao("Quantidade de Títulos em Cobrança Caucionada")
	@Posicao("70,75")
	public long qtdeTitulosCobrancaCaucionada;
	
	@Descricao("Valor Total de Títulos em Cobranca Caucionada")
	@Posicao("76,92")
	public double valorTitulosCobrancaCaucionada;

	// Totalizacao da Cobranca Descontada
	@Descricao("Quantidade de Títulos em Cobrança Descontada")
	@Posicao("93,98")
	public long qtdeTitulosCobrancaDescontada;
	
	@Descricao("Valor Total de Títulos em Cobranca Descontada")
	@Posicao("99,115")
	public double valorTitulosCobrancaDescontada;
	
	@Descricao("Número do Aviso de Lançamento")
	@Posicao("116,123")
	public String numeroAvisoLancamento;
	
	@Descricao("Valor Líquido Crédito Liberado")
	@Posicao("124,140")
	public double valorCreditoLiberado;
	
	@Descricao("Valor Juros Operacao de Desconto")
	@Posicao("141,157")
	public double valorJurosDesconto;
	
	@Descricao("Valor IOF da Operacao de Desconto")
	@Posicao("158,174")
	public double valorIOFDesconto;
	
	@Descricao("Valor Tarifa Operacao Desconto")
	@Posicao("175,191")
	public double valorTarifaDesconto;
	
	@Descricao("Valor do Contrato Limite")
	@Posicao("192,208")
	public double valorContratoLimite;
	
	@Descricao("Saldo Limite Contratado")
	@Posicao("209,225")
	public double saldoLimiteContratado;
	
	@Descricao("Reservado CNAB")
	@Posicao("226,240")
	public String reservadoCNAB226;
	
}
