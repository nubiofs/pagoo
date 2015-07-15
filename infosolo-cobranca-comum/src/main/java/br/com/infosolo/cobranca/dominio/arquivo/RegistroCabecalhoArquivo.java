package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class RegistroCabecalhoArquivo extends Registro240 {

	@Descricao("Reservado CNAB")
	@Posicao("9,17")
	public String reservadoCNAB9;

	/**
	 * Empresa
	 */
	@Descricao("Tipo de Inscrição da Empresa")
	@Posicao("18,18")
	public int tipoInscricaoEmpresa;		// '1' - CPF, '2' - CNPJ
	
	@Descricao("Numero de Inscrição da Empresa")
	@Posicao("19,32")
	public long numeroInscricaoEmpresa;
	
	/**
	 * Convênio
	 */
	@Descricao("Código do Convênio no Banco")
	@Posicao("33,52")
	public String codigoConvenioBanco;	

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
	
	/**
	 * Banco
	 */
	@Descricao("Nome do Banco")
	@Posicao("103,132")
	public String nomeBanco;

	@Descricao("Reservado CNAB")
	@Posicao("133,142")
	public String reservadoCNAB133;

	/**
	 * Arquivo
	 */
	@Descricao("Código Remessa/Retorno")
	@Posicao("143,143")
	public int codigoArquivo;	// '1'= REMESSA, '2'= RETORNO, '3'= RETORNO OPERACAO (DESC)
	
	@Descricao("Data de Geração do Arquivo")
	@Posicao("144,151")
	public String dataGeracaoArquivo;	// (DDMMAAAA)
	
	@Descricao("Hora de Geração do Arquivo")
	@Posicao("152,157")
	public String horaGeracaoArquivo;	// (HHMMSS)
	
	@Descricao("Número Sequencial do Arquivo")
	@Posicao("158,163")
	public long numeroSequencialArquivo;	
	
	@Descricao("Número da Versão do Layout do Arquivo")
	@Posicao("164,166")
	public String versaoLayoutArquivo;	// '010'	
	
	@Descricao("Densidade Gravação do Arquivo")
	@Posicao("167,171")
	public long densidadeGravacaoArquivo;
	
	/**
	 * Outros
	 */
	@Descricao("Duplicatas Nao Aceitas")
	@Posicao("172,172")
	public char duplicNaoAceitas;		// 'S'= SIM, 'N'= NÃO
	
	@Descricao("Número Contrato Limite")
	@Posicao("173,183")
	public String numeroContratoLimite;	// BRANCOS PARA COBRANÇA. NUMERICO PARA DESCONTO
	
	@Descricao("Liberação Automática Oper. Desc.")
	@Posicao("184,184")
	public char liberacaoAutomatica;		// 'S'= SIM, 'N'= NÃO
	
	@Descricao("Reservado Banco")
	@Posicao("185,191")
	public String reservadoBanco;		
	
	@Descricao("Reservado Empresa")
	@Posicao("192,211")
	public String reservadoEmpresa;

	@Descricao("Reservado CNAB")
	@Posicao("212,240")
	public String reservadoCNAB212;

}
