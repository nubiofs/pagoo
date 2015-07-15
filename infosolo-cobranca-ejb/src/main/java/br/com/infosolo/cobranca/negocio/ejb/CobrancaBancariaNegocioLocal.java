package br.com.infosolo.cobranca.negocio.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.infosolo.cobranca.dominio.dto.BancoDTO;
import br.com.infosolo.cobranca.dominio.dto.BoletoDTO;
import br.com.infosolo.cobranca.dominio.dto.CedenteDTO;
import br.com.infosolo.cobranca.dominio.dto.PesquisaBoletoDTO;
import br.com.infosolo.cobranca.dominio.dto.SacadoDTO;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CobrancaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.EnderecoEntidade;
import br.com.infosolo.cobranca.enumeracao.Banco;

@Local
public interface CobrancaBancariaNegocioLocal {
	
	/**
	 * Gera um boleto de cobranca. 
	 * Retorna o mesmo DTO com o campo nosso numero preenchido.
	 * @param boletoDTO
	 * @return
	 */
	public BoletoDTO gerarBoletoCobranca(BoletoDTO boletoDTO);
	
	/**
	 * Registra uma cobranca tendo uma lista de boleto DTO
	 * @param listaBoletosDTO
	 * @return
	 */
	public List<BoletoDTO> registrarCobrancaBancaria(List<BoletoDTO> listaBoletosDTO);
	
	/**
	 * Retorna a entidade de boleto pelo DTO informado.
	 * @param boleto
	 * @param cedenteEntidade
	 * @param cobrancaEntidade
	 * @return
	 */
	public BoletoEntidade retornarNovoBoletoEntidade(BoletoDTO boleto,
			CedenteEntidade cedenteEntidade, CobrancaEntidade cobrancaEntidade);

	/**
	 * Recupera um boleto Entidade executando o contabilizador de acessos. 
	 * @param boletoEntidade contendo a PK populada.
	 * @return
	 */
	public BoletoEntidade recuperarBoleto(BoletoEntidade boletoEntidade);
	
	/**
	 * Recupera um boleto Entidade executando o contabilizador de acessos. 
	 * @param nossoNumero nosso número
	 * @return
	 */
	public BoletoEntidade recuperarBoletoPeloNossoNumero(String nossoNumero, boolean atualizarContador);
	
	public BoletoEntidade recuperarBoletoPeloToken(String token);
	
	/**
	 * Obtem o proximo sequencial de nosso número para o banco informado.
	 * @param banco
	 * @return
	 */
	public long obterSequencialNossoNumero(Banco banco);
	
	/**
	 * Recupera um enderedo de sacado pelo CEP
	 */
	public EnderecoEntidade retornarEnderecoSacadoPorCep(String cpfCnpj, String cep);

	/**
	 * Recupera um enderedo de sacado pelo Email
	 */
	public EnderecoEntidade retornarEnderecoSacadoPorEmail(String cpfCnpj, String email);

	/**
	 * Recupera um enderedo de cedente pelo CEP
	 */
	public EnderecoEntidade retornarEnderecoCedentePorCep(String cpfCnpj, String cep);

	/**
	 * Gera um boleto avulso sem cobranca registrada com os dados informados.
	 * @param cedente
	 * @param sacado
	 * @param dataVencimento
	 * @param valorBoleto
	 * @param numeroDocumento
	 */
	public BoletoDTO gerarBoletoAvulso(CedenteDTO cedente, SacadoDTO sacado, Date dataVencimento, 
			Double valorBoleto, String numeroDocumento, String assuntoEmail, String textoEmail, 
			byte[] anexoRelatorio, byte[] anexoPlanilha, boolean guiaArrecadacao);

	/**
	 * Retorna a lista de bancos existentes.
	 * @return
	 */
	public List<BancoDTO> retornarListaBancos();

	/**
	 * Gera um boleto individual.
	 * @param cedente
	 * @param sacado
	 * @param valorBoleto
	 * @return
	 */
	public BoletoDTO gerarBoletoIndividual(CedenteDTO cedente, SacadoDTO sacado, Double valorBoleto, String numeroDocumento);

	/**
	 * Gera um ou dois boletos de arrecadacao (Guia). 
	 * Se informado mais de um boleto devera ser informado tambem os impostos.
	 * @param boletos
	 * @param impostos
	 * @return
	 */
	public List<BoletoDTO> gerarBoletoArrecadacao(List<BoletoDTO> boletos);

	/**
	 * Retorna o array de bytes do boleto fisico
	 * @param nossoNumero
	 * @return
	 */
	public byte[] retornarBoletoFisico(String nossoNumero, String nossoNumero2);
	
	/**
	 * Retorna a lista de boleto de acordo com os parametros da pesquisa.
	 * @param pesquisaBoleto
	 * @return
	 */
	public List<BoletoDTO> retornarListaBoletos(PesquisaBoletoDTO pesquisaBoleto);

	/**
	 * Envia o email com boleto para o nosso numero informado
	 * @param nossoNumero
	 * @return
	 */
	public boolean enviarEmailCobranca(BoletoDTO boletoDTO);
	
	/**
	 * Retorna verdadeiro se existir boleto de cobranca para o documento informado.
	 * @param numeroDocumento
	 * @return
	 */
	public boolean isExisteBoletoCobranca(String numeroDocumento);

	/**
	 * Gera novamente o boleto fisico pelos dados ja cadastrados nos boletos do banco do 
	 * sistema de cobranca.
	 * @param nossoNumero
	 * @param nossoNumero2
	 * @param instrucoes
	 * @return
	 */
	public byte[] regerarBoletoArrecadacao(String nossoNumero, String nossoNumero2, String instrucoes);

}
