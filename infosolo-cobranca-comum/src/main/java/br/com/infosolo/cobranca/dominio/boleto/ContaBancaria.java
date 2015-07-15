package br.com.infosolo.cobranca.dominio.boleto;

import java.io.Serializable;

import br.com.infosolo.cobranca.enumeracao.Banco;

public class ContaBancaria implements Serializable {
	private static final long serialVersionUID = -3042494739109274079L;
	
	private Banco banco;
	private String agencia = "0";
	private String digitoAgencia = "0";
	private String conta = "0";
	private String digitoConta = "0";
	private String operacaoConta = "";

	public ContaBancaria() {
		
	}
	
	public ContaBancaria(Banco banco, String agencia, String conta) {
		this.banco = banco;
		if (agencia.indexOf('-') > 0) {
			this.digitoAgencia = agencia.substring(agencia.indexOf('-') + 1);
			this.agencia = agencia.substring(0, agencia.indexOf('-'));
		}
		else
			this.agencia = agencia;
		
		if (conta.indexOf('-') > 0) {
			this.digitoConta = conta.substring(conta.indexOf('-') + 1);
			this.conta = conta.substring(0, conta.indexOf('-'));
		}
		else
			this.conta = conta;
	}
	
	public ContaBancaria(Banco banco, String agencia, String digitoAgencia, String conta, String digitoConta) {
		this.banco = banco;
		this.agencia = agencia;
		this.digitoAgencia = digitoAgencia;
		this.conta = conta;
		this.digitoConta = digitoConta;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Banco getBanco() {
		return banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getDigitoAgencia() {
		return digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public String getOperacaoConta() {
		return operacaoConta;
	}

	public void setOperacaoConta(String operacaoConta) {
		this.operacaoConta = operacaoConta;
	}

}
