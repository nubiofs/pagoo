package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de cabeçalho de arquivo de layout FEBRABAN 150
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheA150 extends Registro150 {

	/**
	 * 1 = Remessa - Enviado pela Empresa para o Banco
	 * 2 = Retorno - Enviado pelo Banco para a Empresa
	 */
	@Descricao("Código de Remessa")
	@Posicao("2,2")
	public int codigoRemessa;
	
	@Descricao("Código do Convênio")
	@Posicao("3,22")
	public String codigoConvenio;
	
	@Descricao("Nome da Empresa / Orgão")
	@Posicao("23,42")
	public String nomeEmpresa;
	
	@Descricao("Código do Banco")
	@Posicao("43,45")
	public int codigoBanco;

	@Descricao("Data Geração do Arquivo (AAAAMMDD)")
	@Posicao("66,73")
	public String dataArquivo;

	/**
	 * Este número deverá evoluir de 1 em 1, para cada arquivo gerado, e terá uma 
	 * seqüência para o Banco e outra para a Empresa.
	 * OBS.: O NSA deverá ser rigorosamente observado, pois arquivos que não 
	 * estiverem na seqüência serão rejeitados, implicando no não processamento dos mesmos.
	 */
	@Descricao("Número Sequencial do Arquivo (NSA)")
	@Posicao("74,79")
	public long numeroSequencial;
	
	/**
	 * 04 (a partir de 02.05.2007)
	 */
	@Descricao("Versão do layout")
	@Posicao("80,81")
	public int versaoLayout;
	
	@Descricao("Codigo de Barras")
	@Posicao("82,98")
	public String codigoBarras;

	@Descricao("Reservado para futuro")
	@Posicao("99,150")
	public String reservado;
}
