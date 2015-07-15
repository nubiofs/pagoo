package br.com.infosolo.cobranca.dominio.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the retorno_detalhe database table.
 * 
 */
@Entity
@Table(name="retorno_detalhe",schema = "cobranca")
public class RetornoDetalheEntidade implements Serializable {
	private static final long serialVersionUID = 3962473636529802195L;

	@Id
	@SequenceGenerator(name="RETORNO_DETALHE_IDRETORNODETALHE_GENERATOR", sequenceName="SQ_RETORNO_DETALHE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RETORNO_DETALHE_IDRETORNODETALHE_GENERATOR")
	@Column(name="id_retorno_detalhe", unique=true, nullable=false, precision=131089)
	private long idRetornoDetalhe;

	@Column(name="codigo_movimento", precision=2)
	private Integer codigoMovimento;

    @Temporal( TemporalType.DATE)
	@Column(name="data_credito")
	private Date dataCredito;

    @Temporal( TemporalType.DATE)
	@Column(name="data_ocorrencia")
	private Date dataOcorrencia;

	@Column(name="linha_seguimento_t", length=400)
	private String linhaSeguimentoT;

	@Column(name="linha_seguimento_u", length=400)
	private String linhaSeguimentoU;

	@Column(name="motivo_ocorrencia", length=10)
	private String motivoOcorrencia;

	@Column(name="numero_lote", precision=4)
	private BigDecimal numeroLote;

	@Column(name="numero_registro", precision=131089)
	private BigDecimal numeroRegistro;

	@Column(name="valor_tarifa_boleto", precision=15, scale=2)
	private BigDecimal valorTarifaBoleto;

	//bi-directional many-to-one association to ArquivoRetornoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_arquivo_retorno")
	private ArquivoRetornoEntidade arquivoRetorno;

	//bi-directional many-to-one association to BoletoEntidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_boleto")
	private BoletoEntidadeLite boleto;

    public RetornoDetalheEntidade() {
    }

	public long getIdRetornoDetalhe() {
		return this.idRetornoDetalhe;
	}

	public void setIdRetornoDetalhe(long idRetornoDetalhe) {
		this.idRetornoDetalhe = idRetornoDetalhe;
	}

	public Integer getCodigoMovimento() {
		return this.codigoMovimento;
	}

	public void setCodigoMovimento(Integer codigoMovimento) {
		this.codigoMovimento = codigoMovimento;
	}

	public Date getDataCredito() {
		return this.dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

	public Date getDataOcorrencia() {
		return this.dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public String getLinhaSeguimentoT() {
		return this.linhaSeguimentoT;
	}

	public void setLinhaSeguimentoT(String linhaSeguimentoT) {
		this.linhaSeguimentoT = linhaSeguimentoT;
	}

	public String getLinhaSeguimentoU() {
		return this.linhaSeguimentoU;
	}

	public void setLinhaSeguimentoU(String linhaSeguimentoU) {
		this.linhaSeguimentoU = linhaSeguimentoU;
	}

	public String getMotivoOcorrencia() {
		return this.motivoOcorrencia;
	}

	public void setMotivoOcorrencia(String motivoOcorrencia) {
		this.motivoOcorrencia = motivoOcorrencia;
	}

	public BigDecimal getNumeroLote() {
		return this.numeroLote;
	}

	public void setNumeroLote(BigDecimal numeroLote) {
		this.numeroLote = numeroLote;
	}

	public BigDecimal getNumeroRegistro() {
		return this.numeroRegistro;
	}

	public void setNumeroRegistro(BigDecimal numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public BigDecimal getValorTarifaBoleto() {
		return this.valorTarifaBoleto;
	}

	public void setValorTarifaBoleto(BigDecimal valorTarifaBoleto) {
		this.valorTarifaBoleto = valorTarifaBoleto;
	}

	public ArquivoRetornoEntidade getArquivoRetorno() {
		return this.arquivoRetorno;
	}

	public void setArquivoRetorno(ArquivoRetornoEntidade arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}
	
	public BoletoEntidadeLite getBoleto() {
		return this.boleto;
	}

	public void setBoleto(BoletoEntidadeLite boleto) {
		this.boleto = boleto;
	}
	
}