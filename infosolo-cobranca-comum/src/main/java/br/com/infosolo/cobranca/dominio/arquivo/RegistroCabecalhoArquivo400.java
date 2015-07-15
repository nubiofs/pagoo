package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;


public class RegistroCabecalhoArquivo400 extends Registro400{
	
	@Descricao("Identificação do registro")
	@Posicao("1,1")
	public int codigoRegistro;
	
	@Descricao("Identificação da remessa")
	@Posicao("2,2")
	public int codigoRemessa;
	
	@Descricao("Identificação por extenso")
	@Posicao("3,9")
	public String literalRemessa;
	
	@Descricao("Tipo do serviço")
	@Posicao("10,11")
	public int codigoServico;
	
	@Descricao("Identificação por extenso")
	@Posicao("12,26")
	public String literalServico;
	
	@Descricao("Identificação da empresa no Banco")
	@Posicao("27,36")
	public long codigoEmpresa;
	
	@Descricao("Brancos")
	@Posicao("37,43")
	public String primeiroFiller;
	
	@Descricao("Número da agência")
	@Posicao("44,46")
	public String codigoAgencia;
	
	@Descricao("Nome da empresa por extenso")
	@Posicao("47,76")
	public String nomeEmpresa;
	
	@Descricao("Número do Banco na compensação")
	@Posicao("77,79")
	public int codigoBanco;
	
	@Descricao("Nome do Banco por extenso")
	@Posicao("80,94")
	public String nomeBanco;
	
	@Descricao("Data da gravação do arquivo")
	@Posicao("95,100")
	public String dataGravacao;
	
	@Descricao("Densidade da gravação")
	@Posicao("101,105")
	public int densidade;
	
	@Descricao("Literal de densidade do arquivo")
	@Posicao("106,108")
	public String literalDensidade;
	
	@Descricao("Número do processamento")
	@Posicao("109,115")
	public int numeroProcessamento;
	
	@Descricao("Brancos")
	@Posicao("116,394")
	public String segundoFiller;
	
	@Descricao("Número sequencial do registro")
	@Posicao("395,400")
	public int numeroSequencial;

}
