package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class RegistroRodapeArquivo extends Registro240 {

	@Descricao("Reservado CNAB")
	@Posicao("9,17")
	public String reservadoCNAB9;

	/**
	 * Totais
	 */
	@Descricao("Quantidade de Lotes do Arquivo")
	@Posicao("18,23")
	public long qtdeLotes;	// Registros Tipo = 1
	
	@Descricao("Quantidade de Registros do Arquivo")
	@Posicao("24,29")
	public long qtdeRegistros;	// Registros Tipo = 0+1+3+5+9
	
	@Descricao("Quantidade de Contas para Concil.")
	@Posicao("30,35")
	public long qtdeContasConcil;	

	@Descricao("Reservado CNAB")
	@Posicao("36,240")
	public String reservadoCNAB36;
}
