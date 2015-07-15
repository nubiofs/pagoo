package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class RegistroRodapeArquivo400 extends Registro400 {

	@Descricao("Identificação do registro")
	@Posicao("1,1")
	public int codigoRegistro;
	
	@Descricao("Brancos")
	@Posicao("2,394")
	public long filler;
	
	@Descricao("Número sequencial do registro do arquivo")
	@Posicao("395,400")
	public int numeroSequencial;	
}
