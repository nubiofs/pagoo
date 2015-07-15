package br.com.infosolo.cobranca.dominio.arquivo;

import java.util.ArrayList;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;

public class ArquivoRemessa extends Arquivo {
	private Cedente cedente;
	private ArrayList<Boleto> boletos;

	private RegistroCabecalhoArquivo cabecalho;
	private LoteArquivo[] lotes;
	private RegistroRodapeArquivo rodape;

	public Cedente getCedente() {
		return cedente;
	}

	public void setCedente(Cedente cedente) {
		this.cedente = cedente;
	}

	public ArrayList<Boleto> getBoletos() {
		return boletos;
	}

	public void setBoletos(ArrayList<Boleto> boletos) {
		this.boletos = boletos;
	}

	public RegistroCabecalhoArquivo getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(RegistroCabecalhoArquivo cabecalho) {
		this.cabecalho = cabecalho;
	}

	public LoteArquivo[] getLotes() {
		return lotes;
	}

	public void setLotes(LoteArquivo[] lotes) {
		this.lotes = lotes;
	}

	public RegistroRodapeArquivo getRodape() {
		return rodape;
	}

	public void setRodape(RegistroRodapeArquivo rodape) {
		this.rodape = rodape;
	}

}
