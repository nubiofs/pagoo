package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.enumeracao.TipoArquivo;

public class ArquivoRetorno extends Arquivo {

	public ArquivoRetorno() {
		
	}
	
	public ArquivoRetorno(TipoArquivo tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}
}
