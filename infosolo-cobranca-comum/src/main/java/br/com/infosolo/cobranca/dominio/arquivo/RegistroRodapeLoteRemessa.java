package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

public class RegistroRodapeLoteRemessa extends RegistroRodapeLote {
	
	//
	// Totais
	//

	@Descricao("Quantidade de Registros do Lote")
	@Posicao("18,23")
	public long qtdeRegistros;
	
	@Descricao("Somatória dos Valores")
	@Posicao("24,41")
	public double valorSomatoria;

	@Descricao("Somatória da Quantidade de Moeda")
	@Posicao("42,59")
	public double quantidadeMoeda;

	@Descricao("Reservado CNAB")
	@Posicao("60,240")
	public String reservadoCNAB60;

}
