package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de detalhe de retorno
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheSegmentoT extends RegistroDetalhe {

	//
	// Conta Corrente
	//
	@Descricao("Código Agência Mantenedora da Conta")
	@Posicao("18,22")
	public long agenciaMantenedoraConta;
	
	@Descricao("Dígito Verificador da Agencia")
	@Posicao("23,23")
	public char digitoVerificadorAgencia;
	
	@Descricao("Número da Conta Corrente")
	@Posicao("24,35")
	public long numeroContaCorrente;
	
	@Descricao("Dígito Verificador da Conta")
	@Posicao("36,36")
	public char digitoVerificadorConta;
	
	@Descricao("Dígito Verificador da Agencia/Conta")
	@Posicao("37,37")
	public char digitoVerificadorAgenciaConta;
	
	//
	// Nosso Numero
	//
	
	@Descricao("Nosso Número")
	@Posicao("38,57")
	public String nossoNumero;

	//
	// Cobrança
	//
	@Descricao("Código da Carteira")
	@Posicao("58,58")
	public int codigoCarteira;

	@Descricao("Número Documento")
	@Posicao("59,73")
	public String numeroDocumento;
	
	@Descricao("Data  de Vencimento do Titulo")
	@Posicao("74,81")
	public String dataVencimento;

	@Descricao("Valor Nominal do Título")
	@Posicao("82,96")
	public double valorTitulo;

	@Descricao("Banco do Recebimento da Cobrança")
	@Posicao("97,99")
	public int bancoCobradora;
	
	@Descricao("Agência Encarregada da Cobrança")
	@Posicao("100,104")
	public int agenciaCobradora;
	
	@Descricao("Dígito Verificador da Agencia")
	@Posicao("105,105")
	public char digitoAgenciaCobradora;
	
	@Descricao("Identificação do Título na Empresa")
	@Posicao("106,130")
	public String usoEmpresaCedente;
	
	@Descricao("Código da Moeda")
	@Posicao("131,132")
	public int codigoMoeda;
	
	//
	// Dados do Sacado
	//
	
	@Descricao("Tipo de Inscrição do Sacado")
	@Posicao("133,133")
	public int tipoInscricao;
	
	@Descricao("Número de Inscrição do Sacado")
	@Posicao("134,148")
	public long numeroInscricao;
	
	@Descricao("Nome do Sacado")
	@Posicao("149,188")
	public String nome;
	
	//
	
	@Descricao("Número do Contrado da Operação de Crédito")
	@Posicao("189,198")
	public long numeroContrato;
	
	@Descricao("Valor da Tarifa/Custas")
	@Posicao("199,213")
	public double valorTarifa;
	
	@Descricao("Motivo da Ocorrência")
	@Posicao("214,223")
	public String motivoOcorrencia;
	
	@Descricao("Número Operação")
	@Posicao("224,234")
	public String numeroOperacao;	// Brancos para cobranca / Numerico para desconto

	@Descricao("Reservado CNAB")
	@Posicao("235,240")
	public char reservadoCNAB235;

	public RegistroDetalheSegmentoT() {
		this.segmento = 'T';
	}

}
