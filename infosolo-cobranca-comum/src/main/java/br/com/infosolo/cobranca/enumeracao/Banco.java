package br.com.infosolo.cobranca.enumeracao;

import java.awt.Image;

public enum Banco {
	BANCO_BRASIL(1, 0, "BANCO DO BRASIL S.A."),
	BANCO_CAIXA(104, 0, "CAIXA ECONOMICA FEDERAL"),
	BANCO_HSBC(399, 9, "HSBC"),
	BANCO_BRADESCO(237, 2, "BANCO BRADESCO S.A."),
	BANCO_BICBANCO(320, 4, "BANCO INDUSTRIAL E COMERCIAL S.A.");
	
	private int codigo = 0;
	private int digito = 0;
	private String nome = "";
	private Image imagemLogo;

	private Banco(int codigo, int digito, String nome) {
		this.codigo = codigo;
		this.digito = digito;
		this.nome = nome;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getDigito() {
		return digito;
	}

	public void setDigito(int digito) {
		this.digito = digito;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setImagemLogo(Image imagemLogo) {
		this.imagemLogo = imagemLogo;
	}

	public Image getImagemLogo() {
		return imagemLogo;
	}

	public static Banco findByCodigo(int numero) {
		Banco retorno = null;
		
		Banco[] tipoArray = Banco.values();
		for (int i = 0; i < tipoArray.length; i++) {
			if (tipoArray[i].codigo == numero) {
				retorno = tipoArray[i];
				break;
			}
				
		}
		
		return retorno;
	}	

}
