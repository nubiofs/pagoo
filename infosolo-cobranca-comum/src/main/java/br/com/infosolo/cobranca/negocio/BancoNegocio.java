package br.com.infosolo.cobranca.negocio;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import br.com.infosolo.cobranca.boleto.Contribuinte;
import br.com.infosolo.cobranca.boleto.Convenio;
import br.com.infosolo.cobranca.boleto.OrgaoRecebedor;
import br.com.infosolo.cobranca.boleto.TipoValorReferencia;
import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.boleto.guia.Guia;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.dominio.boleto.Sacado;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.util.Constantes;
import br.com.infosolo.cobranca.util.ConstantesErros;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;

public abstract class BancoNegocio implements CobrancaBancaria {
	private static Logger logger = new Logger(BancoNegocio.class);

	protected Banco banco = null;
	
	protected static String PREFIXO_ARQUIVO_REMESSA = "ARQUIVO_REMESSA";
	
    static {
        Properties properties = new Properties();
		try {
			properties.load(ArquivoNegocio.class.getResourceAsStream(Constantes.COBRANCA_PROPERTIES));
			PREFIXO_ARQUIVO_REMESSA = properties.getProperty("prefixo_arquivo_remessa");
		}
		catch (IOException e) {
			logger.error(ConstantesErros.ERROR_FILE_PROPERTIES + " [" + Constantes.COBRANCA_PROPERTIES + "]");
		}		
    }
	
	public static BancoNegocio getInstance(Banco banco) {
		if (banco == Banco.BANCO_BRASIL) {
			return new BancoBrasilNegocio();
		}
		else if (banco == Banco.BANCO_HSBC) {
			return new BancoHSBCNegocio();
		}
		else if (banco == Banco.BANCO_CAIXA) {
			return new BancoCAIXANegocio();
		}
		return null;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	/**
	 * Retorna o string de cabecalho do arquivo de remessa dependendo do tipo de arquivo.
	 */
	public String retornarCabecalhoArquivoRemesa(Cedente cedente, TipoArquivo tipoArquivo) {
		if (tipoArquivo == TipoArquivo.CNAB240) {
			return retornarCabecalhoArquivoRemesaCNAB240(cedente);
		}
		else if (tipoArquivo == TipoArquivo.CNAB400) {
			return retornarCabecalhoArquivoRemesaCNAB400(cedente);
		}
		return null;
	}

	/**
	 * Retorna o string de cabecalho do arquivo de remessa CNAB240.
	 */
	public String retornarCabecalhoArquivoRemesaCNAB240(Cedente cedente) {
		return null;
	}
	
	/**
	 * Retorna o string de cabecalho do arquivo de remessa CNAB400.
	 */
	public String retornarCabecalhoArquivoRemesaCNAB400(Cedente cedente) {
		return null;
	}
	
	/**
	 * Retorna o string de cabecalho de lote de remessa dependendo do tipo de arquivo.
	 */
	public String retornarCabecalhoLoteRemesa(Cedente cedente, TipoArquivo tipoArquivo) {
		if (tipoArquivo == TipoArquivo.CNAB240) {
			return retornarCabecalhoLoteRemesaCNAB240(cedente);
		}
		else if (tipoArquivo == TipoArquivo.CNAB400) {
			return retornarCabecalhoLoteRemesaCNAB400(cedente);
		}
		return null;
	}

	/**
	 * Retorna o string de cabecalho de lote de remessa CNAB240.
	 */
	public String retornarCabecalhoLoteRemesaCNAB240(Cedente cedente) {
		return null;
	}
	
	/**
	 * Retorna o string de cabecalho de lote de remessa CNAB400.
	 */
	public String retornarCabecalhoLoteRemesaCNAB400(Cedente cedente) {
		return null;
	}

	/**
	 * Retorna o string de rodape de lote de remessa dependendo do tipo de arquivo.
	 */
	public String retornarRodapeLoteRemesa(int qtdeRegistros, TipoArquivo tipoArquivo) {
		if (tipoArquivo == TipoArquivo.CNAB240) {
			return retornarRodapeLoteRemesaCNAB240(qtdeRegistros);
		}
		else if (tipoArquivo == TipoArquivo.CNAB400) {
			return retornarRodapeLoteRemesaCNAB400(qtdeRegistros);
		}
		return null;
	}
	
	/**
	 * Retorna o string de rodape de lote de remessa CNAB240.
	 */
	public String retornarRodapeLoteRemesaCNAB240(int qtdeRegistros) {
		return null;
	}
	
	/**
	 * Retorna o string de rodape de lote de remessa CNAB400.
	 */
	public String retornarRodapeLoteRemesaCNAB400(int qtdeRegistros) {
		return null;
	}

	/**
	 * Retorna o string de rodape de arquivo de remessa dependendo do tipo de arquivo.
	 */
	public String retornarRodapeArquivoRemesa(int qtdeRegistros, TipoArquivo tipoArquivo) {
		if (tipoArquivo == TipoArquivo.CNAB240) {
			return retornarRodapeArquivoRemesaCNAB240(qtdeRegistros);
		}
		else if (tipoArquivo == TipoArquivo.CNAB400) {
			return retornarRodapeArquivoRemesaCNAB400(qtdeRegistros);
		}
		return null;
	}

	/**
	 * Retorna o string de rodape de arquivo de remessa CNAB240.
	 */
	public String retornarRodapeArquivoRemesaCNAB240(int qtdeRegistros) {
		return null;
	}
	
	/**
	 * Retorna o string de rodape de arquivo de remessa CNAB240.
	 */
	public String retornarRodapeArquivoRemesaCNAB400(int qtdeRegistros) {
		return null;
	}

	/**
	 * Retorna o string de detalhe tipo segmento P de remessa CNAB240.
	 */
	public String retornarDetalheSegmentoPRemessa(int numeroRegistro, Cedente cedente, Boleto boleto) {
		return null;
	}

	/**
	 * Retorna o string de detalhe tipo segmento Q de remessa CNAB240.
	 */
	public String retornarDetalheSegmentoQRemessa(int numeroRegistro, Boleto boleto) {
		return null;
	}
	
	/**
	 * Retorna o string de detalhe de remessa CNAB240.
	 */
	public String retornarDetalheRemessaCNAB400(int numeroRegistro, Boleto boleto) {
		return null;
	}
	
	/**
	 * Calcula o digito verificador do Nosso Numero
	 */
	public int calculaDVNossoNumero(long numero) {
		// O DAC (Digito de Auto-Conferencia) utilizado aqui é módulo 11
		String strNumero = String.valueOf(numero);
		int soma = 0, peso = 0, resto = 0, digito = -1; 
		
		// Calcula a soma do produto de cada caracter pelo peso
		// peso alterna de 2 a 9
		for (int i = (strNumero.length() - 1); i >= 0; i--) {
			if (peso < 2 | peso >= 9 ) {
				peso = 2;
			} else {
				peso++;
			}
			
			int num = Integer.parseInt(strNumero.substring(i,i+1));
			
			num = (num * peso);
			
			soma += num;
		}
	
		// Calcula o resto da divisão da soma por 11
		resto = soma % 11;

		if (resto < 2) {
			//Se o resto da divisão for zero o dígito será zero
			digito = 0;
		} else {				
			//Senão o digito será 10 menos o resto da divisão
			digito = 11 - resto;				
		}							

		return digito;
	}
	
	/**
	 * Formata o Nosso Numero com Digito Verificador
	 * @return
	 */
	public String formatarNossoNumero(long codigoCedente, long sequencial, int tamanho) {
		String strCedente = String.valueOf(codigoCedente);
		if (strCedente.length() > 5)
			strCedente = strCedente.substring(strCedente.length() - 5);
		int tamanhoSequencial = tamanho - strCedente.length() - 1;
		String nossoNumero = strCedente + String.format("%0" + tamanhoSequencial + "d", sequencial);
		
		long numero = Long.parseLong(nossoNumero); 
		nossoNumero = nossoNumero + String.valueOf(calculaDVNossoNumero(numero));
		
		if (nossoNumero.length() != 11 && nossoNumero.length() != 14 && nossoNumero.length() != 17) {
			throw new CobrancaExcecao("Tamanho do campo nosso número inválido: ");
		}
		
		return nossoNumero;
	}

	/**
	 * Formata nosso numero utilizando 4 digitos para cedente.
	 * 
	 * @param codigoCedente
	 * @param sequencial
	 * @param tamanho
	 * @return
	 */
	public String formatarNossoNumeroBB(long codigoCedente, long sequencial, int tamanho) {
		if (codigoCedente == 0) codigoCedente = 9999;
		
		String strCedente = String.valueOf(codigoCedente);
		String nossoNumero = "";
		
		if (strCedente.length() <= 6) {
			// Nosso Numero com 11 digitos
			if (strCedente.length() > 4)
				strCedente = strCedente.substring(strCedente.length() - 4);
			int tamanhoSequencial = tamanho - 5;
			nossoNumero = strCedente + String.format("%0" + tamanhoSequencial + "d", sequencial);
		}
		else {
			// Nosso Numero com 17 digitos
			// OBS: O BB usa nosso numero de 17 posições para convenios acima de 1.000.000 (um milhão)
			int lendoc = 9;
			if (strCedente.length() <= 7)
				lendoc = 16 - strCedente.length();
			else
				strCedente = strCedente.substring(strCedente.length() - 7);
			
			nossoNumero = String.format("%s", strCedente) + String.format("%0" + lendoc + "d", sequencial);
		}
		
		long numero = Long.parseLong(nossoNumero);
		
		return nossoNumero + String.valueOf(calculaDVNossoNumero(numero));
	}

	/**
	 * Retorna um objeto Boleto com todos os dados do cedente e sacados devidamente informados.
	 */
	public Boleto obterBoleto(Cedente cedente, Date dataVencimento, double valorBoleto, 
			long numeroDocumento, Sacado sacado, String nossoNumero) {
		return null;
	}

	/**
	 * Retorna um objeto Guia com todos os dados devidamente informados.
	 */
	public Guia obterGuia(Cedente cedente, Date dataVencimento, double valorBoleto, 
			long numeroDocumento, Sacado sacado, String nossoNumero) {
		return null;
	}

	/**
	 * Retorna um objeto Guia com todos os dados devidamente informados.
	 * Este objeto podera ter um segundo cedente.
	 */
	public Guia obterGuia(Cedente cedente, Cedente cedente2, Date dataVencimento, Double valorBoleto, Double valorBoleto2,  
			long numeroDocumento, Sacado sacado, String nossoNumero, String nossoNumero2) {
		
		Guia guia = null;
		
		Contribuinte contribuinte = new Contribuinte(sacado.getNome(), sacado.getCpfCnpj());
		contribuinte.setEndereco(sacado.getEndereco());
		
		OrgaoRecebedor orgaoRecebedor = new OrgaoRecebedor(cedente.getNome(), cedente.getCpfCnpj(), null);
		Convenio convenio = new Convenio(cedente.getContaBancaria().getBanco(), (int) cedente.getConvenio());
		Arrecadacao arrecadacao = new Arrecadacao(convenio, orgaoRecebedor, contribuinte);

		if (arrecadacao != null) {
			arrecadacao.setTitulo("RECIBO DO SACADO");
			arrecadacao.setDescricao("Guia de Recebimento n�o Compens�vel para Pagamento");
			
			arrecadacao.setNossoNumero(nossoNumero);
			arrecadacao.setValorDocumento(valorBoleto);
			arrecadacao.setTipoValorReferencia(TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10);
			arrecadacao.setDataDoDocumento(new Date());				
			arrecadacao.setDataDoVencimento(dataVencimento);
			arrecadacao.setNumeroDoDocumento(String.valueOf(numeroDocumento));

			Arrecadacao arrecadacao2 = null;
			
			if (cedente2 != null) {
				OrgaoRecebedor orgaoRecebedor2 = new OrgaoRecebedor(cedente2.getNome(), cedente.getCpfCnpj(), null);
				Convenio convenio2 = new Convenio(cedente2.getContaBancaria().getBanco(), (int) cedente2.getConvenio());
				arrecadacao2 = new Arrecadacao(convenio2, orgaoRecebedor2, contribuinte);
				
				arrecadacao2.setNossoNumero(nossoNumero2);
				arrecadacao2.setValorDocumento(valorBoleto2);
				arrecadacao2.setTipoValorReferencia(TipoValorReferencia.VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10);
				arrecadacao2.setDataDoDocumento(new Date());				
				arrecadacao2.setDataDoVencimento(dataVencimento);
				arrecadacao2.setNumeroDoDocumento(String.valueOf(numeroDocumento));
			}
			
			guia = new Guia(arrecadacao, arrecadacao2);
			guia.setCedente(cedente);
			guia.setCedente2(cedente2);
		}

		return guia;
	}

	/**
	 * Calcula o nome do arquivo para geração de remessa.
	 * @return
	 */
	public String calcularNomeArquivoRemessa(Long codigoCedente, String nome) {
		String nomeArquivo =  String.format("%s/%d/in/%s_%s_%s.TXT", 
				ArquivoNegocio.getDiretorioArquivosRemessa(), codigoCedente,
				BancoNegocio.PREFIXO_ARQUIVO_REMESSA, nome, 
				DataUtil.formatarData(new Date(), "ddMMyyyyHHmmss"));
		return nomeArquivo;
	}

	/**
	 * Calcula o nome do arquivo para geração de remessa.
	 * @return
	 */
	public String calcularNomeArquivoRemessa(Long codigoCedente) {
		return calcularNomeArquivoRemessa(codigoCedente, banco.name());
	}


}
