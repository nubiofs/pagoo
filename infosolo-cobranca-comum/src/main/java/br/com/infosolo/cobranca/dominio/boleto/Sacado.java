package br.com.infosolo.cobranca.dominio.boleto;

import java.util.ArrayList;


public class Sacado extends Pessoa {
	private ArrayList<String> informacoes;

	public Sacado() {
		
	}
	
	public Sacado(String cpfCnpj, String nome) {
		super(cpfCnpj, nome);
	}

	public Sacado(String cpfCnpj, String nome, Endereco endereco) {
		super(cpfCnpj, nome);
		this.endereco = endereco;
	}

	/**
	 * Lista de todas as informações para serem apresentadas abaixo do nome do sacado.
	 */
	public ArrayList<String> getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(ArrayList<String> informacoes) {
		this.informacoes = informacoes;
	}

}
