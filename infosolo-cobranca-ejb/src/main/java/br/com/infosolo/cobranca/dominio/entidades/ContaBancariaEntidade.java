package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the conta_bancaria database table.
 * 
 */
@Entity
@Table(name="conta_bancaria",schema = "cobranca")
public class ContaBancariaEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTA_BANCARIA_IDCONTA_GENERATOR", sequenceName="SQ_CONTA_BANCARIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTA_BANCARIA_IDCONTA_GENERATOR")
	@Column(name="id_conta", unique=true, nullable=false, precision=131089)
	private Long idConta;

	@Column(nullable=false, length=10)
	private String agencia;

	@Column(nullable=false, length=10)
	private String conta;

	@Column(name="digito_agencia", nullable=false, length=1)
	private String digitoAgencia;

	@Column(name="digito_conta", nullable=false, length=1)
	private String digitoConta;

	@Column(name="operacao_conta", nullable=false, length=10)
	private String operacaoConta;

	//bi-directional many-to-one association to CedenteEntidade
	@OneToMany(mappedBy="contaBancaria")
	private List<CedenteEntidade> cedentes;

	//bi-directional many-to-one association to BancoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_banco", nullable=false)
	private BancoEntidade banco;

    public ContaBancariaEntidade() {
    }

	public Long getIdConta() {
		return this.idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return this.conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDigitoAgencia() {
		return this.digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getDigitoConta() {
		return this.digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public String getOperacaoConta() {
		return this.operacaoConta;
	}

	public void setOperacaoConta(String operacaoConta) {
		this.operacaoConta = operacaoConta;
	}

	public List<CedenteEntidade> getCedentes() {
		return this.cedentes;
	}

	public void setCedentes(List<CedenteEntidade> cedentes) {
		this.cedentes = cedentes;
	}
	
	public BancoEntidade getBanco() {
		return this.banco;
	}

	public void setBanco(BancoEntidade banco) {
		this.banco = banco;
	}
	
}