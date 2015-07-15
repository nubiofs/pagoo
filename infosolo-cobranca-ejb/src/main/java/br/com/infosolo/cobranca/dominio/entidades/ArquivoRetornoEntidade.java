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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the arquivo_retorno database table.
 * 
 */
@Entity
@Table(name="arquivo_retorno",schema = "cobranca")
@NamedQueries({
	@NamedQuery(name="arquivoRetorno.retornarArquivoRetornoPorNomeArquivo", 
			   query="FROM ArquivoRetornoEntidade arquivo WHERE arquivo.nomeArquivoRetorno = :nomeArquivoRetorno"), 
})
public class ArquivoRetornoEntidade implements Serializable {
	private static final long serialVersionUID = 6415269855324560171L;

	@Id
	@SequenceGenerator(name="ARQUIVO_RETORNO_IDARQUIVORETORNO_GENERATOR", sequenceName="SQ_ARQUIVO_RETORNO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARQUIVO_RETORNO_IDARQUIVORETORNO_GENERATOR")
	@Column(name="id_arquivo_retorno", unique=true, nullable=false, precision=131089)
	private Long idArquivoRetorno;

	@Column(name="arquivo_fisico")
	private byte[] arquivoFisico;

	@Column(name="data_arquivo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataArquivo;

	@Column(name="nome_arquivo_retorno", length=1000)
	private String nomeArquivoRetorno;

	//bi-directional many-to-one association to LeiauteArquivoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_leiaute")
	private LeiauteArquivoEntidade leiauteArquivo;

	//bi-directional many-to-many association to BoletoEntidade
	@ManyToMany(mappedBy="arquivoRetornos")
	private List<BoletoEntidade> boletos;

	@OneToMany(mappedBy="arquivoRetorno")
	private List<RetornoDetalheEntidade> retornoDetalhes;
	
    public ArquivoRetornoEntidade() {
    }

	public Long getIdArquivoRetorno() {
		return this.idArquivoRetorno;
	}

	public void setIdArquivoRetorno(Long idArquivoRetorno) {
		this.idArquivoRetorno = idArquivoRetorno;
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

	public String getNomeArquivoRetorno() {
		return this.nomeArquivoRetorno;
	}

	public void setNomeArquivoRetorno(String nomeArquivoRetorno) {
		this.nomeArquivoRetorno = nomeArquivoRetorno;
	}

	public LeiauteArquivoEntidade getLeiauteArquivo() {
		return this.leiauteArquivo;
	}

	public void setLeiauteArquivo(LeiauteArquivoEntidade leiauteArquivo) {
		this.leiauteArquivo = leiauteArquivo;
	}
	
	public List<BoletoEntidade> getBoletos() {
		return this.boletos;
	}

	public void setBoletos(List<BoletoEntidade> boletos) {
		this.boletos = boletos;
	}

	public List<RetornoDetalheEntidade> getRetornoDetalhes() {
		return retornoDetalhes;
	}

	public void setRetornoDetalhes(List<RetornoDetalheEntidade> retornoDetalhes) {
		this.retornoDetalhes = retornoDetalhes;
	}
	
}