package br.com.infosolo.cobranca.enumeracao;

/**
 * Tipo do Movimento também conhecido como Tipo de Operação
 * Indica a operacao que deverá ser realizada com os registros Detalhe do Lote.
 * Deve constar apenas um tipo por Lote:
 *   R = Arquivo Remessa.
 *   T = Arquivo Retorno.
 *   O = Arquivo Retorno Operação (somente para desconto).
 *   
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 */
public enum TipoMovimento {
	REMESSA(1),
	RETORNO(2),
	RETORNO_DESCONTO(3);
	
	private int value;
	
	private TipoMovimento(int value) {
		this.setValue(value);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static TipoMovimento findByValor(int numero) {
		TipoMovimento retorno = null;
		
		TipoMovimento[] tipoArray = TipoMovimento.values();
		for (int i = 0; i < tipoArray.length; i++) {
			if (tipoArray[i].value == numero) {
				retorno = tipoArray[i];
				break;
			}
				
		}
		
		return retorno;
	}	
	
}
