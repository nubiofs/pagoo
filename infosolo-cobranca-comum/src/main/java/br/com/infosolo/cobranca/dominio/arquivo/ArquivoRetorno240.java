package br.com.infosolo.cobranca.dominio.arquivo;

import java.util.ArrayList;

import br.com.infosolo.cobranca.enumeracao.TipoArquivo;

public class ArquivoRetorno240 extends ArquivoRetorno {
	private RegistroCabecalhoArquivo cabecalho;
	private ArrayList<LoteArquivo> lotes = new ArrayList<LoteArquivo>();
	private RegistroRodapeArquivo rodape;

	public ArquivoRetorno240() {
		this.tipoArquivo = TipoArquivo.CNAB240;
	}

	public void setCabecalho(RegistroCabecalhoArquivo cabecalho) {
		this.cabecalho = cabecalho;
	}

	public RegistroCabecalhoArquivo getCabecalho() {
		return cabecalho;
	}

	public void setLotes(ArrayList<LoteArquivo> lotes) {
		this.lotes = lotes;
	}

	public ArrayList<LoteArquivo> getLotes() {
		return lotes;
	}

	public void setRodape(RegistroRodapeArquivo rodape) {
		this.rodape = rodape;
	}

	public RegistroRodapeArquivo getRodape() {
		return rodape;
	}

}
