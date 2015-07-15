package br.com.infosolo.cobranca.enumeracao;

public enum TipoArquivo {
	CNAB240(1),
	CNAB400(2),
	FEBRABAN150(10),
	OUTRO(99);
	
	private int codigo;
	
	private TipoArquivo(int codigo) {
		this.setCodigo(codigo);
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public static TipoArquivo findByCodigo(int numero) {
		TipoArquivo retorno = null;
		
		TipoArquivo[] tipoArray = TipoArquivo.values();
		for (int i = 0; i < tipoArray.length; i++) {
			if (tipoArray[i].codigo == numero) {
				retorno = tipoArray[i];
				break;
			}
				
		}
		
		return retorno;
	}	
	
}
