package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de detalhe de remessa contendo os dados de endereco do sacado.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheSegmentoQ extends RegistroDetalhe {

	//
	// Dados do Sacado
	//
	
	@Descricao("Tipo de Inscrição")
	@Posicao("18,18")
	public int tipoInscricao;
	
	@Descricao("Número de Inscrição")
	@Posicao("19,33")
	public long numeroInscricao;
	
	@Descricao("Nome")
	@Posicao("34,73")
	public String nome;
	
	@Descricao("Endereço")
	@Posicao("74,111")
	public String endereco;
	
	@Descricao("Uso do Banco")
	@Posicao("112,113")
	public String usoBanco;
	
	@Descricao("Bairro")
	@Posicao("114,128")
	public String bairro;
	
	@Descricao("CEP")
	@Posicao("129,136")
	public long cep;
	
	@Descricao("Cidade")
	@Posicao("137,151")
	public String cidade;
	
	@Descricao("Estado")
	@Posicao("152,153")
	public String estado;

	//
	// Sacador Avalista
	//
	
	@Descricao("Tipo de Inscrição Avalista")
	@Posicao("154,154")
	public int tipoInscricaoAvalista;
	
	@Descricao("Número de Inscrição Avalista")
	@Posicao("155,169")
	public long numeroInscricaoAvalista;
	
	@Descricao("Nome Avalista")
	@Posicao("170,209")
	public String nomeAvalista;
	
	//
	
	@Descricao("Uso do Banco")
	@Posicao("210,232")
	public String usoBanco2;
	
	@Descricao("Reservado CNAB")
	@Posicao("233,240")
	public String reservadoCNA232;
	
	public RegistroDetalheSegmentoQ() {
		this.segmento = 'Q';
	}
}
