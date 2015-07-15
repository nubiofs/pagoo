package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the leiaute_arquivo database table.
 * 
 */
@Entity
@Table(name="leiaute_arquivo",schema = "cobranca")
public class LeiauteArquivoEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LEIAUTE_ARQUIVO_IDLEIAUTE_GENERATOR", sequenceName="SQ_LEIAUTE_ARQUIVO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LEIAUTE_ARQUIVO_IDLEIAUTE_GENERATOR")
	@Column(name="id_leiaute", unique=true, nullable=false, precision=131089)
	private Long idLeiaute;

	@Column(length=200)
	private String descricao;

	@Column(nullable=false, length=20)
	private String nome;

	//bi-directional many-to-one association to ArquivoRemessaEntidade
	@OneToMany(mappedBy="leiauteArquivo")
	private List<ArquivoRemessaEntidade> arquivoRemessas;

	//bi-directional many-to-one association to ArquivoRetornoEntidade
	@OneToMany(mappedBy="leiauteArquivo")
	private List<ArquivoRetornoEntidade> arquivoRetornos;

    public LeiauteArquivoEntidade() {
    }

	public Long getIdLeiaute() {
		return this.idLeiaute;
	}

	public void setIdLeiaute(Long idLeiaute) {
		this.idLeiaute = idLeiaute;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ArquivoRemessaEntidade> getArquivoRemessas() {
		return this.arquivoRemessas;
	}

	public void setArquivoRemessas(List<ArquivoRemessaEntidade> arquivoRemessas) {
		this.arquivoRemessas = arquivoRemessas;
	}
	
	public List<ArquivoRetornoEntidade> getArquivoRetornos() {
		return this.arquivoRetornos;
	}

	public void setArquivoRetornos(List<ArquivoRetornoEntidade> arquivoRetornos) {
		this.arquivoRetornos = arquivoRetornos;
	}
	
}