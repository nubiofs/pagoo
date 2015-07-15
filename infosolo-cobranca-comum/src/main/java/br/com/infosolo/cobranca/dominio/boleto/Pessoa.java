package br.com.infosolo.cobranca.dominio.boleto;

import br.com.infosolo.cobranca.enumeracao.TipoInscricao;
import br.com.infosolo.comum.util.TextoUtil;

public class Pessoa {
	protected String nome;
	protected TipoInscricao tipoInscricao;
	protected String cpfCnpj;
	protected Endereco endereco;
	protected ContaBancaria contaBancaria;

	public Pessoa() {
		
	}
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	
	public Pessoa(String cpfCnpj, String nome) {
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		
		if (cpfCnpj.length() <= 11)
			tipoInscricao = TipoInscricao.CPF;
		else
			tipoInscricao = TipoInscricao.CNPJ;
	}

	/**
	 * Retorna verdadeiro se for pessoa f�sica.
	 * @return
	 */
	public boolean isPessoaFisica() {
		return tipoInscricao == TipoInscricao.CPF;
	}
	
	/**
	 * Retorna verdadeiro se for pessoa jur�dica.
	 * @return
	 */
	public boolean isPessoaJuridica() {
		return tipoInscricao == TipoInscricao.CNPJ;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = TextoUtil.retiraSimbolos(cpfCnpj);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public void setTipoInscricao(TipoInscricao tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public TipoInscricao getTipoInscricao() {
		return tipoInscricao;
	}

}
