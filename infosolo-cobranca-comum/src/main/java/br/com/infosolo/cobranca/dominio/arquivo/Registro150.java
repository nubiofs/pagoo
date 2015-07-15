package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Classe Pai para registros do arquivo para layout FEBRABAN 150.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class Registro150 extends Registro {

	@Descricao("Código do Registro")
	@Posicao("1,1")
	public char codigoRegistro;

}
