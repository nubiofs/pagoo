package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de trailler de arquivo de layout FEBRABAN 150
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheZ150 extends Registro150 {

	@Descricao("Total de registros de arquivo")
	@Posicao("2,7")
	public long totalRegistros;

	@Descricao("Valor total recebido dos registros do arquivo")
	@Posicao("8,24")
	public double valorTotal;

	@Descricao("Reservado para futuro")
	@Posicao("25,150")
	public String reservado;

}
