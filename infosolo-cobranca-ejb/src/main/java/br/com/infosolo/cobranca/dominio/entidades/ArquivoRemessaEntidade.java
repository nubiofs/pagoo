package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the arquivo_remessa database table.
 * 
 */
@Entity
@Table(name="arquivo_remessa",schema = "cobranca")
public class ArquivoRemessaEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ARQUIVO_REMESSA_IDARQUIVOREMESSA_GENERATOR", sequenceName="SQ_ARQUIVO_REMESSA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARQUIVO_REMESSA_IDARQUIVOREMESSA_GENERATOR")
	@Column(name="id_arquivo_remessa", unique=true, nullable=false)
	private Long idArquivoRemessa;

	@Column(name="arquivo_fisico")
	private byte[] arquivoFisico;

	@Column(name="data_arquivo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataArquivo;

	@Column(name="nome_arquivo_remessa", length=1000)
	private String nomeArquivoRemessa;

	//bi-directional many-to-one association to LeiauteArquivoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_leiaute")
	private LeiauteArquivoEntidade leiauteArquivo;

	//bi-directional many-to-one association to CobrancaEntidade
	@OneToMany(mappedBy="arquivoRemessa")
	private List<CobrancaEntidade> cobrancas;

    public ArquivoRemessaEntidade() {
    }

	public Long getIdArquivoRemessa() {
		return this.idArquivoRemessa;
	}

	public void setIdArquivoRemessa(Long idArquivoRemessa) {
		this.idArquivoRemessa = idArquivoRemessa;
	}

	public byte[] getArquivoFisico() {
		return this.arquivoFisico;
	}

	public void setArquivoFisico(byte[] arquivoFisico) {
		this.arquivoFisico = arquivoFisico;
	}

	public Date getDataArquivo() {
		return this.dataArquivo;
	}

	public void setDataArquivo(Date dataArquivo) {
		this.dataArquivo = dataArquivo;
	}

	public String getNomeArquivoRemessa() {
		return this.nomeArquivoRemessa;
	}

	public void setNomeArquivoRemessa(String nomeArquivoRemessa) {
		this.nomeArquivoRemessa = nomeArquivoRemessa;
	}

	public LeiauteArquivoEntidade getLeiauteArquivo() {
		return this.leiauteArquivo;
	}

	public void setLeiauteArquivo(LeiauteArquivoEntidade leiauteArquivo) {
		this.leiauteArquivo = leiauteArquivo;
	}
	
	public List<CobrancaEntidade> getCobrancas() {
		return this.cobrancas;
	}

	public void setCobrancas(List<CobrancaEntidade> cobrancas) {
		this.cobrancas = cobrancas;
	}
	
}