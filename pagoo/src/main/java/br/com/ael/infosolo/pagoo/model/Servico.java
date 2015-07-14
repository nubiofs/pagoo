package br.com.ael.infosolo.pagoo.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;


/**
 * The persistent class for the servico database table.
 * 
 */
@Entity
@Table(name="servico")
@NamedQuery(name="Servico.findAll", query="SELECT s FROM Servico s")
@XmlRootElement
public class Servico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, precision=10, scale=4)
	private BigDecimal valor;

	@Column(name="valor_repasse", nullable=false, precision=10, scale=4)
	private BigDecimal valorRepasse;

	//bi-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="id_evento", nullable=false)
	private Evento evento;

	//bi-directional many-to-one association to TipoServico
	@ManyToOne
	@JoinColumn(name="id_tipo_servico", nullable=false)
	private TipoServico tipoServico;

    @NotNull
    @NotEmpty
    @Column(name="placa",length=7)
    @Size(min=7)
	private String placa;
	
    
	public Servico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorRepasse() {
		return this.valorRepasse;
	}

	public void setValorRepasse(BigDecimal valorRepasse) {
		this.valorRepasse = valorRepasse;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public TipoServico getTipoServico() {
		return this.tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}