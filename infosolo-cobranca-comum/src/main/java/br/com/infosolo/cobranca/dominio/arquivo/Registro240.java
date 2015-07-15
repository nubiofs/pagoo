package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class Registro240 extends Registro {

	/**
	 * Campos Controle
	 */
	@Descricao("Código do Banco na Compensação")
	@Posicao("1,3")
	public int banco;

	@Descricao("Lote de Serviço")
	@Posicao("4,7")
	public int lote;
	
	@Descricao("Tipo de Registro")
	@Posicao("8,8")
	public int tipoRegistro;

}
