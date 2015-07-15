package br.com.infosolo.cobranca.negocio;

import java.util.Date;

import br.com.infosolo.cobranca.boleto.guia.Guia;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.dominio.boleto.Sacado;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;

public interface CobrancaBancaria {
	public String retornarCabecalhoArquivoRemesa(Cedente cedente, TipoArquivo tipoArquivo);
	public String retornarCabecalhoArquivoRemesaCNAB240(Cedente cedente);
	public String retornarCabecalhoArquivoRemesaCNAB400(Cedente cedente);
	
	public String retornarCabecalhoLoteRemesa(Cedente cedente, TipoArquivo tipoArquivo);
	public String retornarCabecalhoLoteRemesaCNAB240(Cedente cedente);
	public String retornarCabecalhoLoteRemesaCNAB400(Cedente cedente);
	
	public String retornarRodapeLoteRemesa(int qtdeRegistros, TipoArquivo tipoArquivo);
	public String retornarRodapeLoteRemesaCNAB240(int qtdeRegistros);
	public String retornarRodapeLoteRemesaCNAB400(int qtdeRegistros);

	public String retornarRodapeArquivoRemesa(int qtdeRegistros, TipoArquivo tipoArquivo);
	public String retornarRodapeArquivoRemesaCNAB240(int qtdeRegistros);
	public String retornarRodapeArquivoRemesaCNAB400(int qtdeRegistros);
	
	public String retornarDetalheSegmentoPRemessa(int numeroRegistro, Cedente cedente, Boleto boleto);
	public String retornarDetalheSegmentoQRemessa(int numeroRegistro, Boleto boleto);
	
	public int calculaDVNossoNumero(long numero);
	
	public Boleto obterBoleto(Cedente cedente, Date dataVencimento, double valorBoleto, 
			long numeroDocumento, Sacado sacado, String nossoNumero);
	
	public Guia obterGuia(Cedente cedente, Date dataVencimento, double valorBoleto, 
			long numeroDocumento, Sacado sacado, String nossoNumero);
	
	public String formatarNossoNumero(long codigoCedente, long sequencial, int tamanho);
	
}
