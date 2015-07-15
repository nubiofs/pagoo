package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;


/**
 * Registro de detalhe de remessa contendo os dados de valores do titulo bancario.
 * 
 * @author Lazaro Prates (lazaro.prates@infosolo.com.br)
 *
 */
public class RegistroDetalhe400 extends Registro400 {
	
	@Descricao("Identificação do registro")
	@Posicao("1,1")
	public int codigoRegistro; //Igual a 1(um)
	
	@Descricao("Tipo de inscrição da Empresa")
	@Posicao("2,3")
	public int codigoInscricao; // 01 = CPF / 02 = CGC
	
	@Descricao("Número de inscrição da Empresa")
	@Posicao("4,17")
	public int numeroInscricao;
	
	@Descricao("Identificação da empresa no Banco")
	@Posicao("18,27")
	public int codigoEmpresa;
	
	@Descricao("Brancos")
	@Posicao("28,37")
	public int primeiroFiller;
	
	@Descricao("Campo livre para uso da Empresa")
	@Posicao("38,62")
	public String usoDaEmpresa;
	
	@Descricao("Identificação do título do Banco")
	@Posicao("63,70")
	public String nossoNumero;
	
	@Descricao("Brancos")
	@Posicao("71,107")
	public int segundoFiller;
	
	@Descricao("Código da carteira")
	@Posicao("108,108")
	public int carteira; // 1 = Cobrança Simples / 4 = Cobrança caucionada
	
	@Descricao("Código da ocorrência")
	@Posicao("109,110")
	public int codigoOcorrencia;
	
	@Descricao("Número do título")
	@Posicao("111,120")
	public String seuNumero;
	
	@Descricao("Vencimento do título")
	@Posicao("121,126")
	public int vencimento;
	
	@Descricao("Valor do título")
	@Posicao("127,139")
	public double valorTitulo;
	
	@Descricao("Banco engarregado da cobrança")
	@Posicao("140,142")
	public int bancoCobrador;
	
	@Descricao("Agência engarrada da cobrança, mais próximo do sacado")
	@Posicao("143,147")
	public String agenciaCobradora;
	
	@Descricao("Espécie do documento")
	@Posicao("148,149")
	public int especie;
	
	@Descricao("Identificação do aceite")
	@Posicao("150,150")
	public char aceite; // A = Aceito / N = Não aceito
	
	@Descricao("Data de emissão do título")
	@Posicao("151,156")
	public String dataEmissao;
	
	@Descricao("1ª instrução de cobrança")
	@Posicao("157,158")
	public String instrucao01;
	
	@Descricao("2ª instrução de cobrança")
	@Posicao("159,160")
	public String instrucao02;
	
	@Descricao("Valor da mora por dia de atraso")
	@Posicao("161,173")
	public double jurosPorDia;
	
	@Descricao("Data limite para concessão de desconto")
	@Posicao("174,179")
	public String descontoAte;
	
	@Descricao("Valor do desconto")
	@Posicao("180,192")
	public double valorDoDesconto;
	
	@Descricao("Valor do IOF - Seguro")
	@Posicao("193,205")
	public double valorDoIOF;
	
	@Descricao("Valor do abatimento")
	@Posicao("206,218")
	public long abatimento;
	
	@Descricao("Tipo de inscrição do sacado")
	@Posicao("219,220")
	public int codigoInscricaoSacado; // 01 = CPF / 02 = CGC
	
	@Descricao("Número de inscrição do sacado")
	@Posicao("221,234")
	public int numeroInscricaoSacado;
	
	@Descricao("Nome do sacado")
	@Posicao("235,274")
	public String nomeDoSacado;
	
	@Descricao("Endereço do sacado")
	@Posicao("275,308")
	public String endereco;
	
	@Descricao("Brancos")
	@Posicao("309,314")
	public int terceiroFiller;
	
	@Descricao("Bairro do sacado")
	@Posicao("315,326")
	public String bairro;
	
	@Descricao("CEP do sacado")
	@Posicao("327,334")
	public int cep;
	
	@Descricao("Praça do sacado")
	@Posicao("335,349")
	public String praca;
	
	@Descricao("Sigla do Estado do sacado")
	@Posicao("350,351")
	public String estado;
	
	@Descricao("Nome do Sacador/Analista ou Mensagem")
	@Posicao("352,391")
	public String sacadorOuAnalistaOuMensagem;
	
	@Descricao("Quantidade de dias para protesto")
	@Posicao("392,393")
	public int prazo;
	
	@Descricao("Código da moeda")
	@Posicao("394,394")
	public int moeda;
	
	@Descricao("Número sequencial do registro do arquivo")
	@Posicao("395,400")
	public int numeroSequencial;
}
