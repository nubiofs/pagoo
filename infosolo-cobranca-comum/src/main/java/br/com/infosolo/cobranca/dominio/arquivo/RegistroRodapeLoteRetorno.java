package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class RegistroRodapeLoteRetorno extends RegistroRodapeLote {

	//
	// Dados da Empresa
	//
	
	@Descricao("Tipo de Inscrição da Empresa")
	@Posicao("18,18")
	public int tipoInscricaoEmpresa;
	
	@Descricao("Número de Inscrição da Empresa")
	@Posicao("19,32")
	public long numeroInscricaoEmpresa;

	@Descricao("Código do Convênio no Banco")
	@Posicao("33,52")
	public long codigoConvenioBanco;
	
	//
	// Conta Corrente
	//
	@Descricao("Código Agência Mantenedora da Conta")
	@Posicao("53,57")
	public long agenciaMantenedoraConta;
	
	@Descricao("Dígito Verificador da Agencia")
	@Posicao("58,58")
	public char digitoVerificadorAgencia;
	
	@Descricao("Número da Conta Corrente")
	@Posicao("59,70")
	public long numeroContaCorrente;
	
	@Descricao("Dígito Verificador da Conta")
	@Posicao("71,71")
	public char digitoVerificadorConta;
	
	@Descricao("Dígito Verificador da Agencia/Conta")
	@Posicao("72,72")
	public char digitoVerificadorAgenciaConta;
	
	@Descricao("Reservado CNAB")
	@Posicao("73,88")
	public String reservadoCNA073;
	
	//
	// Valores
	//
	
	@Descricao("Saldo Bloqueado Acima 24 horas")
	@Posicao("89,106")
	public double valorSaldoBloqueadoAcima24Horas;
	
	@Descricao("Limite da Conta")
	@Posicao("107,124")
	public double valorLimiteConta;

	@Descricao("Saldo Bloqueado até 24 Horas")
	@Posicao("125,142")
	public double valorSaldoBloqueadoAte24Horas;
	
	// 
	// Saldo Final 
	//
	
	@Descricao("Data do Saldo Final")
	@Posicao("143,150")
	public String dataSaldoFinal;
	
	@Descricao("Valor do Saldo Final")
	@Posicao("151,168")
	public double valorSaldoFinal;
		
	@Descricao("Situação do Saldo Final")
	@Posicao("169,169")
	public char situacaoSaldoFinal;

	@Descricao("Posição do Saldo Final")
	@Posicao("170,170")
	public char posicaoSaldoFinal;
	
	//
	// Totais
	//
	
	@Descricao("Quantidade de Registros do Lote")
	@Posicao("171,176")
	public String quantidadeRegistrosLote;
	
	@Descricao("Somatória dos Valores a Débito")
	@Posicao("177,194")
	public double somatorioValoresDebito;
	
	@Descricao("Somatória dos Valores a Crédito")
	@Posicao("195,212")
	public double somatorioValoresCredito;
	
	@Descricao("Reservado CNAB")
	@Posicao("213,240")
	public String reservadoCNA213;
	
}
