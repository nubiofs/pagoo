package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the especie_titulo database table.
 * 
 */
@Entity
@Table(name="especie_titulo",schema = "cobranca")
public class EspecieTituloEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESPECIE_TITULO_CODIGOESPECIETITULO_GENERATOR", sequenceName="SQ_ESPECIE_TITULO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESPECIE_TITULO_CODIGOESPECIETITULO_GENERATOR")
	@Column(name="codigo_especie_titulo", unique=true, nullable=false, precision=131089)
	private Long codigoEspecieTitulo;

	@Column(nullable=false, length=150)
	private String descricao;

	@Column(name="sigla_febraban", nullable=false, length=5)
	private String siglaFebraban;

	//bi-directional many-to-one association to BoletoEntidade
	@OneToMany(mappedBy="especieTitulo")
	private List<BoletoEntidade> boletos;

    public EspecieTituloEntidade() {
    }

	public Long getCodigoEspecieTitulo() {
		return this.codigoEspecieTitulo;
	}

	public void setCodigoEspecieTitulo(Long codigoEspecieTitulo) {
		this.codigoEspecieTitulo = codigoEspecieTitulo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSiglaFebraban() {
		return this.siglaFebraban;
	}

	public void setSiglaFebraban(String siglaFebraban) {
		this.siglaFebraban = siglaFebraban;
	}

	public List<BoletoEntidade> getBoletos() {
		return this.boletos;
	}

	public void setBoletos(List<BoletoEntidade> boletos) {
		this.boletos = boletos;
	}
	
}