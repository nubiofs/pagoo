package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Header de Lote - Registro 0
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroCabecalhoLote extends Registro240 {
	/**
	 * Campos Serviço
	 */
	@Descricao("Tipo de Operação")
	@Posicao("9,9")
	public char tipoOperacao;		// 'C'

	@Descricao("Tipo de Serviço")
	@Posicao("10,11")
	public String tipoServico;		// '29'

	@Descricao("Reservado CNAB")
	@Posicao("12,13")
	public String reservadoCNAB12;

	@Descricao("Layout do Lote")
	@Posicao("14,16")
	public String layoutLote;			// '010'
	
	@Descricao("Reservado CNAB")
	@Posicao("17,17")
	public char reservadoCNAB17;
	
	/**
	 * Empresa
	 */
	@Descricao("Tipo de Inscrição da Empresa")
	@Posicao("18,18")
	public int tipoInscricaoEmpresa;		// '1' - CPF, '2' - CNPJ
	
	@Descricao("Numero de Inscrição da Empresa")
	@Posicao("19,33")
	public long numeroInscricaoEmpresa;
	
	/**
	 * Convênio
	 */
	@Descricao("Código do Contrato Cobrança")
	@Posicao("34,53")
	public String codigoContratoCobranca;
	
	/**
	 * Conta Corrente
	 */
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

	/**
	 * Nome
	 */
	@Descricao("Nome da Empresa")
	@Posicao("73,102")
	public String nomeEmpresa;

	@Descricao("Reservado CNAB")
	@Posicao("103,142")
	public String reservadoCNAB103;

	/**
	 * Adicionais encontrado no BB
	 */
	
	/**
	 * Saldo Inicial
	 */

	@Descricao("Data do Saldo Inicial")
	@Posicao("143,150")
	public String dataSaldoInicial;
	
	@Descricao("Valor do Saldo Inicial")
	@Posicao("151,168")
	public long valorSaldoInicial;
	
	@Descricao("Situacao do Saldo Inicial")
	@Posicao("169,169")
	public char situacaoSaldoInicial;
	
	@Descricao("Posicao do Saldo Inicial")
	@Posicao("170,170")
	public char posicaoSaldoInicial;

	@Descricao("Moeda Referenciada no Extrato")
	@Posicao("171,173")
	public String moedaReferenciadaExtrato;

	@Descricao("Número de Seqüência do Extrato")
	@Posicao("174,178")
	public long numeroSequenciaExtrato;

	@Descricao("Reservado CNAB")
	@Posicao("179,240")
	public String reservadoCNAB179;

}