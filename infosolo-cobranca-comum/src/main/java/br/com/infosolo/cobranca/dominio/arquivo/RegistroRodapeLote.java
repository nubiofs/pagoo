package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class RegistroRodapeLote extends Registro240 {
	
	@Descricao("Reservado CNAB")
	@Posicao("9,17")
	public String reservadoCNAB9;
	
}
