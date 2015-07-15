package br.com.infosolo.cobranca.dominio.boleto;

public class SacadorAvalista extends Pessoa {

	public SacadorAvalista() {
		
	}
	
	public SacadorAvalista(String cpfCnpj, String nome) {
		super(cpfCnpj, nome);
	}

	public SacadorAvalista(String cpfCnpj, String nome, Endereco endereco) {
		super(cpfCnpj, nome);
		this.endereco = endereco;
	}

}
