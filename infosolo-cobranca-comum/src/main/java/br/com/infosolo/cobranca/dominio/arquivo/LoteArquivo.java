package br.com.infosolo.cobranca.dominio.arquivo;

import java.util.ArrayList;

/**
 * Representa registro do tipo de lote.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class LoteArquivo {
	private RegistroCabecalhoLote cabecalho;
	private ArrayList<RegistroDetalhe> registros = new ArrayList<RegistroDetalhe>();
	private RegistroRodapeLote rodape;
	
	private ArrayList<String> linhas = new ArrayList<String>();

	public RegistroCabecalhoLote getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(RegistroCabecalhoLote cabecalho) {
		this.cabecalho = cabecalho;
	}

	public RegistroRodapeLote getRodape() {
		return rodape;
	}

	public void setRodape(RegistroRodapeLote rodape) {
		this.rodape = rodape;
	}

	public void setRegistros(ArrayList<RegistroDetalhe> registros) {
		this.registros = registros;
	}

	public ArrayList<RegistroDetalhe> getRegistros() {
		return registros;
	}

	public void setLinhas(ArrayList<String> linhas) {
		this.linhas = linhas;
	}

	public ArrayList<String> getLinhas() {
		return linhas;
	}

}
