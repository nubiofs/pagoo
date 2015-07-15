package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cobranca database table.
 * 
 */
@Entity
@Table(name="cobranca",schema = "cobranca")
public class CobrancaEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COBRANCA_IDCOBRANCA_GENERATOR", sequenceName="SQ_COBRANCA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COBRANCA_IDCOBRANCA_GENERATOR")
	@Column(name="id_cobranca", unique=true, nullable=false, precision=131089)
	private Long idCobranca;

	//bi-directional many-to-one association to BoletoEntidade
	@OneToMany(mappedBy="cobranca")
	private List<BoletoEntidade> boletos;

	//bi-directional many-to-one association to ArquivoRemessaEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_arquivo_remessa", nullable=true)
	private ArquivoRemessaEntidade arquivoRemessa;

    public CobrancaEntidade() {
    }

	public Long getIdCobranca() {
		return this.idCobranca;
	}

	public void setIdCobranca(Long idCobranca) {
		this.idCobranca = idCobranca;
	}

	public List<BoletoEntidade> getBoletos() {
		return this.boletos;
	}

	public void setBoletos(List<BoletoEntidade> boletos) {
		this.boletos = boletos;
	}
	
	public ArquivoRemessaEntidade getArquivoRemessa() {
		return this.arquivoRemessa;
	}

	public void setArquivoRemessa(ArquivoRemessaEntidade arquivoRemessa) {
		this.arquivoRemessa = arquivoRemessa;
	}
	
}