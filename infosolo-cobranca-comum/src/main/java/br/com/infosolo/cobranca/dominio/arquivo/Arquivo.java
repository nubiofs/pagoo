package br.com.infosolo.cobranca.dominio.arquivo;

import java.util.Date;

import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;

public class Arquivo {
	protected String nomeArquivo;
	protected Banco banco;
	protected TipoArquivo tipoArquivo;
	protected Date dataArquivo;

	public Date getDataArquivo() {
		return dataArquivo;
	}

	public void setDataArquivo(Date dataArquivo) {
		this.dataArquivo = dataArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public TipoArquivo getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(TipoArquivo tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

}
