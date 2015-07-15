package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de detalhe de remessa contendo os dados de valores do titulo bancario.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheSegmentoP extends RegistroDetalhe {

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
	
	@Descricao("Tipo de Cadastramento")
	@Posicao("59,59")
	public int tipoCadastramento;		// 1 - Com Cadastramento; 2 - Sem Cadastramento
	
	@Descricao("Tipo de Documento")
	@Posicao("60,60")
	public int tipoDocumento;			// 1 - Tradicional; 2 - Escritural
	
	@Descricao("Identificação da Emissão de Bloqueto")
	@Posicao("61,61")
	public int identificacaoBloqueto;			
	
	@Descricao("Identificação da Distribuição")
	@Posicao("62,62")
	public int identificacaoDistribuicao;	// 1 - Banco Distribui; 2 - Cliente Distribui			
	
	
	@Descricao("Número Documento")
	@Posicao("63,77")
	public String numeroDocumento;
	
	@Descricao("Data  de Vencimento do Titulo")
	@Posicao("78,85")
	public String dataVencimento;

	@Descricao("Valor Nominal do Título")
	@Posicao("86,100")
	public double valorTitulo;

	@Descricao("Agência Encarregada da Cobrança")
	@Posicao("101,105")
	public int agenciaCobradora;
	
	@Descricao("Dígito Verificador da Agencia")
	@Posicao("106,106")
	public char digitoAgenciaCobradora;
	
	@Descricao("Espécie do Título")
	@Posicao("107,108")
	public int especieTitulo;
	
	@Descricao("Identificação de Título Aceito")
	@Posicao("109,109")
	public char aceite;			// A - Aceite; N - Não aceite
	
	@Descricao("Data  de Emissão do Titulo")
	@Posicao("110,117")
	public String dataEmissao;
	
	//
	// Juros
	//
	
	@Descricao("Código do Juros de Mora")
	@Posicao("118,118")
	public int codigoJurosMora;

	@Descricao("Data do Juros de Mora")
	@Posicao("119,126")
	public String dataJurosMora;

	@Descricao("Valor de Mora por Dia/Taxa")
	@Posicao("127,141")
	public double valorJurosMora;

	//
	// Desc1
	//
	
	@Descricao("Código do Desconto 1")
	@Posicao("142,142")
	public int codigoDesconto;

	@Descricao("Data do Desconto 1")
	@Posicao("143,150")
	public String dataDesconto;

	@Descricao("Valor/Percentual a ser Concedido")
	@Posicao("151,165")
	public double valorDesconto;

	@Descricao("Valor do IOF a ser Recolhido")
	@Posicao("166,180")
	public double valorIOF;

	@Descricao("Valor do Abatimento")
	@Posicao("181,195")
	public double valorAbatimento;

	@Descricao("Identificação do Título na Empresa")
	@Posicao("196,220")
	public String usoEmpresaCedente;
	
	@Descricao("Código para Protesto")
	@Posicao("221,221")
	public int codigoProtesto;	

	@Descricao("Número de Dias para Protesto")
	@Posicao("222,223")
	public int numeroDiasProtesto;
	
	@Descricao("Código para Baixa/Devolução")
	@Posicao("224,224")
	public int codigoBaixa;	// 1 - Baixar/Devolver; 2 - Não Baixar/Não Devolver	
	
	@Descricao("Número de Dias para Baixa")
	@Posicao("225,227")
	public int numeroDiasBaixa;
	
	@Descricao("Código da Moeda")
	@Posicao("228,229")
	public int codigoMoeda;
	
	@Descricao("Número do Contrato")
	@Posicao("230,239")
	public long numeroContrato;
	
	@Descricao("Reservado CNAB")
	@Posicao("240,240")
	public char reservadoCNAB240;
	
	public RegistroDetalheSegmentoP() {
		this.segmento = 'P';
	}
	
}
