package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="instrucoes_bancarias",schema = "cobranca")
public class InstrucoesBancariaEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private InstrucoesBancariaEntidadePK id;

	@Id
	@SequenceGenerator(name="INSTRUCOES_BANCARIAS_IDINSTRUCAOBANCARIA_GENERATOR", sequenceName="SQ_INSTRUCOES_BANCARIAS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTRUCOES_BANCARIAS_IDINSTRUCAOBANCARIA_GENERATOR")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_instrucao_bancaria", unique=true, nullable=false, precision=4)
	private Long idInstrucaoBancaria;

	@Column(length=300)
	private String descricao;

	//bi-directional many-to-one association to CedenteEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="codigo_convenio", referencedColumnName="codigo_convenio"),
		@JoinColumn(name="numero_cpf_cnpj_cedente", referencedColumnName="numero_cpf_cnpj_cedente")
		})
	private CedenteEntidade cedente;

    public InstrucoesBancariaEntidade() {
    }

//	public void setId(InstrucoesBancariaEntidadePK id) {
//		this.id = id;
//	}
//
//	public InstrucoesBancariaEntidadePK getId() {
//		return id;
//	}

	public void setIdInstrucaoBancaria(Long idInstrucaoBancaria) {
		this.idInstrucaoBancaria = idInstrucaoBancaria;
	}

	public Long getIdInstrucaoBancaria() {
		return idInstrucaoBancaria;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CedenteEntidade getCedente() {
		return this.cedente;
	}

	public void setCedente(CedenteEntidade cedente) {
		this.cedente = cedente;
	}
	
}