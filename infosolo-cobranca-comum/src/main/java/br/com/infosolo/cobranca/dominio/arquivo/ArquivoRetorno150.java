package br.com.infosolo.cobranca.dominio.arquivo;

import java.util.ArrayList;

import br.com.infosolo.cobranca.enumeracao.TipoArquivo;

public class ArquivoRetorno150 extends ArquivoRetorno {
	private RegistroDetalheA150 cabecalho;
	private ArrayList<RegistroDetalheG150> registros = new ArrayList<RegistroDetalheG150>();
	private RegistroDetalheZ150 rodape;

	private ArrayList<String> linhas = new ArrayList<String>();

	public ArquivoRetorno150() {
		this.tipoArquivo = TipoArquivo.FEBRABAN150;
	}
	
	public RegistroDetalheA150 getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(RegistroDetalheA150 cabecalho) {
		this.cabecalho = cabecalho;
	}

	public ArrayList<RegistroDetalheG150> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<RegistroDetalheG150> registros) {
		this.registros = registros;
	}

	public RegistroDetalheZ150 getRodape() {
		return rodape;
	}

	public void setRodape(RegistroDetalheZ150 rodape) {
		this.rodape = rodape;
	}

	public void setLinhas(ArrayList<String> linhas) {
		this.linhas = linhas;
	}

	public ArrayList<String> getLinhas() {
		return linhas;
	}

}
