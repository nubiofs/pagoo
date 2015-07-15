package br.com.infosolo.cobranca.dominio.boleto;

import java.io.Serializable;

import br.com.infosolo.cobranca.enumeracao.TipoCobranca;

public class Cedente extends Pessoa implements Serializable {
	private static final long serialVersionUID = -6142902631987133035L;

	private ContaBancaria contaBancaria;
	private long codigo = 0;
	private long convenio = 0;
	private int digitoCedente = -1;
	private String localPagamento;
	private String instrucaoSacado;
	private String carteira;
	private String descricaoConcedente;
	
	// Talvez tenha que retirar daqui, pois isso se refere a Carteira
	private TipoCobranca tipoCobranca = TipoCobranca.NAO_REGISTRADA;
	
	public Cedente() {
		
	}

	public Cedente(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}
	
	public Cedente(String cpfCnpj, String nome) {
		super(cpfCnpj, nome);
	}
	
	public Cedente(String cpfCnpj, String nome, ContaBancaria contaBancaria) {
		super(cpfCnpj, nome);
		setContaBancaria(contaBancaria);
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public long getConvenio() {
		return convenio;
	}

	public void setConvenio(long convenio) {
		this.convenio = convenio;
	}

	public int getDigitoCedente() {
		return digitoCedente;
	}

	public void setDigitoCedente(int digitoCedente) {
		this.digitoCedente = digitoCedente;
	}

	public void setTipoCobranca(TipoCobranca tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public TipoCobranca getTipoCobranca() {
		return tipoCobranca;
	}

	public void setInstrucaoSacado(String instrucaoSacado) {
		this.instrucaoSacado = instrucaoSacado;
	}

	public String getInstrucaoSacado() {
		return instrucaoSacado;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public String getLocalPagamento() {
		return localPagamento;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getCarteira() {
		return carteira;
	}

	public String getDescricaoConcedente() {
		return descricaoConcedente;
	}

	public void setDescricaoConcedente(String descricaoConcedente) {
		this.descricaoConcedente = descricaoConcedente;
	}

}
