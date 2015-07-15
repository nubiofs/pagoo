package br.com.infosolo.cobranca.dominio.boleto;

import java.io.Serializable;
import java.util.Date;

import br.com.infosolo.cobranca.boleto.CodigoBarras;
import br.com.infosolo.cobranca.boleto.LinhaDigitavel;
import br.com.infosolo.cobranca.boleto.ParametrosBancariosMap;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivre;
import br.com.infosolo.cobranca.enumeracao.EspecieTitulo;
import br.com.infosolo.cobranca.enumeracao.TipoMoeda;
import br.com.infosolo.comum.util.TextoUtil;

public class Boleto implements Serializable {
	private static final long serialVersionUID = -5162436248379294845L;

	private String carteira;
	private String nossoNumero;
	private Date dataVencimento;
	private Date dataEmissao;
	private Date dataProcessamento;
	private double valorBoleto;
	private EspecieTitulo especieTitulo = EspecieTitulo.OU;
	private long numeroDocumento;
	private double valorJurosMora;
	private double valorIOF;
	private double valorAbatimento;
	private double valorMulta;
	private double valorDesconto;
	private Cedente cedente;
	private Sacado sacado;
	private SacadorAvalista sacadorAvalista;
	private String instrucaoSacado;
	private String instrucao1;
	private String instrucao2;
	private String instrucao3;
	private String instrucao4;
	private String instrucao5;
	private String instrucao6;
	private String instrucao7;
	private String instrucao8;
	private String localPagamento;
	private String identificacaoAceite;
	private int quantidadeDiasProtesto;
	private TipoMoeda tipoMoeda;

	private CodigoBarras codigoBarras;
	private CampoLivre campoLivre;
	private ParametrosBancariosMap parametrosBancariosMap;
	private LinhaDigitavel linhaDigitavel;

	/**
	 *<p>
	 * Map com nome do campo e imagem para este campo.
	 *</p>
	 */
	//private Map<String, Image> imagensExtras; 

	public Boleto() {
		this.dataEmissao = new Date();
		this.dataProcessamento = new Date();
		this.tipoMoeda = TipoMoeda.REAL;
	}
	
	public Boleto(Date dataVencimento, double valorBoleto, String carteira, long numeroDocumento, 
			EspecieTitulo especieTitulo, Cedente cedente, Sacado sacado) {
		this.dataVencimento = dataVencimento;
		this.valorBoleto = valorBoleto;
		this.carteira = carteira;
		this.numeroDocumento = numeroDocumento;
		this.setEspecieTitulo(especieTitulo);
		this.sacado = sacado;
		this.cedente = cedente;
		this.dataEmissao = new Date();
		this.dataProcessamento = new Date();
		this.tipoMoeda = TipoMoeda.REAL;
	}
	
	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setValorBoleto(double valorBoleto) {
		this.valorBoleto = valorBoleto;
	}

	public double getValorBoleto() {
		return valorBoleto;
	}

	public void setEspecieTitulo(EspecieTitulo especieTitulo) {
		this.especieTitulo = especieTitulo;
	}

	public EspecieTitulo getEspecieTitulo() {
		return especieTitulo;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public double getValorDesconto() {
		return valorDesconto;
	}

	public void setNumeroDocumento(long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public long getNumeroDocumento() {
		return numeroDocumento;
	}

	public double getValorJurosMora() {
		return valorJurosMora;
	}

	public void setValorJurosMora(double valorJurosMora) {
		this.valorJurosMora = valorJurosMora;
	}

	public double getValorIOF() {
		return valorIOF;
	}

	public void setValorIOF(double valorIOF) {
		this.valorIOF = valorIOF;
	}

	public double getValorAbatimento() {
		return valorAbatimento;
	}

	public void setValorAbatimento(double valorAbatimento) {
		this.valorAbatimento = valorAbatimento;
	}

	public double getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(double valorMulta) {
		this.valorMulta = valorMulta;
	}

	public void setSacado(Sacado sacado) {
		this.sacado = sacado;
	}

	public Sacado getSacado() {
		return sacado;
	}

	public void setSacadorAvalista(SacadorAvalista sacadorAvalista) {
		this.sacadorAvalista = sacadorAvalista;
	}

	public SacadorAvalista getSacadorAvalista() {
		return sacadorAvalista;
	}
	
	public String getInstrucaoSacado() {
		return instrucaoSacado;
	}

	public void setInstrucaoSacado(String instrucaoSacado) {
		this.instrucaoSacado = instrucaoSacado;
	}

	public String getInstrucao1() {
		return instrucao1;
	}

	public void setInstrucao1(String instrucao1) {
		this.instrucao1 = instrucao1;
	}

	public String getInstrucao2() {
		return instrucao2;
	}

	public void setInstrucao2(String instrucao2) {
		this.instrucao2 = instrucao2;
	}

	public String getInstrucao3() {
		return instrucao3;
	}

	public void setInstrucao3(String instrucao3) {
		this.instrucao3 = instrucao3;
	}

	public String getInstrucao4() {
		return instrucao4;
	}

	public void setInstrucao4(String instrucao4) {
		this.instrucao4 = instrucao4;
	}

	public String getInstrucao5() {
		return instrucao5;
	}

	public void setInstrucao5(String instrucao5) {
		this.instrucao5 = instrucao5;
	}

	public String getInstrucao6() {
		return instrucao6;
	}

	public void setInstrucao6(String instrucao6) {
		this.instrucao6 = instrucao6;
	}

	public String getInstrucao7() {
		return instrucao7;
	}

	public void setInstrucao7(String instrucao7) {
		this.instrucao7 = instrucao7;
	}

	public String getInstrucao8() {
		return instrucao8;
	}

	public void setInstrucao8(String instrucao8) {
		this.instrucao8 = instrucao8;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public String getLocalPagamento() {
		return localPagamento;
	}

	public void setIdentificacaoAceite(String identificacaoAceite) {
		this.identificacaoAceite = identificacaoAceite;
	}

	public String getIdentificacaoAceite() {
		return identificacaoAceite;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setCedente(Cedente cedente) {
		this.cedente = cedente;
	}

	public Cedente getCedente() {
		return cedente;
	}

	public void setQuantidadeDiasProtesto(int quantidadeDiasProtesto) {
		this.quantidadeDiasProtesto = quantidadeDiasProtesto;
	}

	public int getQuantidadeDiasProtesto() {
		return quantidadeDiasProtesto;
	}

	public void setTipoMoeda(TipoMoeda tipoMoeda) {
		this.tipoMoeda = tipoMoeda;
	}

	public TipoMoeda getTipoMoeda() {
		return tipoMoeda;
	}

	public void setCodigoBarras(CodigoBarras codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public CodigoBarras getCodigoBarras() {
		return codigoBarras;
	}

	public void setCampoLivre(CampoLivre campoLivre) {
		TextoUtil.checkNotNull(campoLivre);
		int length = campoLivre.write().length();
		
		if (length == CampoLivre.TAMANHO_CAMPO_LIVRE) {
			this.campoLivre = campoLivre;
		} else {
			if (length > CampoLivre.TAMANHO_CAMPO_LIVRE) {
				throw new IllegalArgumentException("O tamanho da String [" + length + "] é maior que o especificado [" + CampoLivre.TAMANHO_CAMPO_LIVRE + "]!");
			} else {
				throw new IllegalArgumentException("O tamanho da String [" + length + "] é menor que o especificado [" + CampoLivre.TAMANHO_CAMPO_LIVRE + "]!");
			}
		}
	}

	public CampoLivre getCampoLivre() {
		return campoLivre;
	}

	public void setParametrosBancarios(ParametrosBancariosMap parametrosBancariosMap) {
		this.parametrosBancariosMap = parametrosBancariosMap;
	}

	public ParametrosBancariosMap getParametrosBancarios() {
		return parametrosBancariosMap;
	}

	public void setLinhaDigitavel(LinhaDigitavel linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	public LinhaDigitavel getLinhaDigitavel() {
		return linhaDigitavel;
	}

//	public void setImagensExtras(Map<String, Image> imagensExtras) {
//		this.imagensExtras = imagensExtras;
//	}
//
//	public Map<String, Image> getImagensExtras() {
//		return imagensExtras;
//	}
//
//	public void addImagensExtras(String fieldName, Image image) {
//		if(imagensExtras == null) {
//			setImagensExtras(new HashMap<String, Image>());
//		}
//		imagensExtras.put(fieldName, image);
//	}

}
