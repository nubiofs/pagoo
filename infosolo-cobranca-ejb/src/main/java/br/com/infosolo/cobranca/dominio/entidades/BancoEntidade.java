package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the banco database table.
 * 
 */
@Entity
@Table(name="banco",schema = "cobranca")
@NamedQueries({
	@NamedQuery(name="retornarListaBancos", query="FROM BancoEntidade banco") 
})
public class BancoEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_banco", unique=true, nullable=false, precision=131089)
	private Long codigoBanco;

	@Column(name="nome_banco", nullable=false, length=200)
	private String nomeBanco;

	//bi-directional many-to-one association to ContaBancariaEntidade
	@OneToMany(mappedBy="banco")
	private List<ContaBancariaEntidade> contaBancarias;

    public BancoEntidade() {
    }

	public Long getCodigoBanco() {
		return this.codigoBanco;
	}

	public void setCodigoBanco(Long codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getNomeBanco() {
		return this.nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public List<ContaBancariaEntidade> getContaBancarias() {
		return this.contaBancarias;
	}

	public void setContaBancarias(List<ContaBancariaEntidade> contaBancarias) {
		this.contaBancarias = contaBancarias;
	}
	
}