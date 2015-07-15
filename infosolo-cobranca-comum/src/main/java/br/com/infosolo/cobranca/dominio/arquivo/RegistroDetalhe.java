package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro base de detalhe para demais tipos de registros de remessa e retorno.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalhe extends Registro240 {

	//
	// Campos Serviço
	//
	@Descricao("Número do Registro")
	@Posicao("9,13")
	public long numeroRegistro;

	@Descricao("Segmento")
	@Posicao("14,14")
	public char segmento;

	@Descricao("Reservado CNAB")
	@Posicao("15,15")
	public char reservadoCNAB15;

	@Descricao("Código de Movimento")
	@Posicao("16,17")
	public int codigoMovimento;

}
