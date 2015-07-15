package br.com.infosolo.cobranca.negocio;

import java.util.Date;

import br.com.infosolo.cobranca.boleto.CodigoBarras;
import br.com.infosolo.cobranca.boleto.LinhaDigitavel;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivre;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivreFabrica;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoArquivo400;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalhe400;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeArquivo400;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.dominio.boleto.Sacado;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.EspecieTitulo;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.util.ArquivoFabrica;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;

public class BancoBradescoNegocio extends BancoNegocio {
	
	private static Logger logger = new Logger(BancoBradescoNegocio.class);


	public BancoBradescoNegocio() {
		banco = Banco.BANCO_BRADESCO;	
	}
	
	/**
	 * Retorna o string de cabecalho do arquivo de remessa CNAB400.
	 */
	public String retornarCabecalhoArquivoRemesaCNAB400(Cedente cedente) {
		String linha = "";
		Date dataAtual = new Date();
		RegistroCabecalhoArquivo400 cabecalho400 = new RegistroCabecalhoArquivo400();
		
		cabecalho400.codigoRegistro = 0;
		cabecalho400.codigoRemessa = 1;
		cabecalho400.literalRemessa = "REMESSA";
		cabecalho400.codigoServico = 01;
		cabecalho400.literalServico = "COBRANÇA";
		//Estudar como fazer esse
		cabecalho400.codigoEmpresa = cedente.getCodigo();
		cabecalho400.codigoAgencia = cedente.getContaBancaria().getAgencia();
		cabecalho400.nomeEmpresa = cedente.getNome();
		cabecalho400.codigoBanco = 320;
		cabecalho400.nomeBanco = "BICBANCO";
		cabecalho400.dataGravacao = DataUtil.formatarData(dataAtual,"ddMMyy");
		cabecalho400.densidade = 01600;
		cabecalho400.literalDensidade = "BPI";
		//Arrumar
		cabecalho400.numeroProcessamento = 123;
		cabecalho400.numeroSequencial = 000001;
		
		linha = ArquivoFabrica.gerarLinha(cabecalho400, TipoArquivo.CNAB400);
		
		return linha;
	}
	
	/**
	 * Retorna o string de rodape de arquivo de remessa CNAB400.
	 */
	public String retornarRodapeArquivoRemesaCNAB400(int qtdeRegistros) {
		String linha = "";
		
		RegistroRodapeArquivo400 rodapeArquivo400 = new RegistroRodapeArquivo400();
		rodapeArquivo400.codigoRegistro = 9;
		//Arrumar
		rodapeArquivo400.numeroSequencial = 123456;
		
		linha = ArquivoFabrica.gerarLinha(rodapeArquivo400, TipoArquivo.CNAB400);
		

		return linha;
	}
	
	@Override
	public String retornarDetalheRemessaCNAB400(int numeroRegistro, Boleto boleto) {
		String linha = "";
		
		RegistroDetalhe400 detalhe400 = new RegistroDetalhe400();
		detalhe400.codigoRegistro = 1;
		//Arrumar
		detalhe400.codigoInscricao = 123;
		// Arrumar
		detalhe400.numeroInscricao = 222;
		//Arrumar
		detalhe400.codigoEmpresa = 222;
		//Arrumar
		detalhe400.usoDaEmpresa = "";
		detalhe400.nossoNumero = "123456";
		//Arrumar
		detalhe400.carteira = 22;
		//Arrumar
		detalhe400.codigoOcorrencia = 10;
		//Arrumar - boleto.getNumeroDocumento()
		detalhe400.seuNumero = "258";
		//Arrumar
		detalhe400.vencimento = 123;
		
		detalhe400.valorTitulo = boleto.getValorBoleto();
		//Arrumar
		detalhe400.bancoCobrador = 123;
		//Arrumar
		detalhe400.agenciaCobradora = "456";
		
		detalhe400.especie = 01;
		
		detalhe400.aceite = 'A';
		
		detalhe400.dataEmissao = DataUtil.formatarData(boleto.getDataVencimento(),"ddMMyy");
		
		detalhe400.instrucao01 = boleto.getInstrucao1();
		detalhe400.instrucao02 = boleto.getInstrucao2();
		
		detalhe400.jurosPorDia = boleto.getValorJurosMora();
		detalhe400.descontoAte = DataUtil.formatarData(new Date(), "ddMMyy");
		
		detalhe400.valorDoDesconto = boleto.getValorDesconto();
		
		detalhe400.valorDoIOF = boleto.getValorIOF();
		//Arrumar
		detalhe400.abatimento = 123;
		//Arrumar
		detalhe400.codigoInscricaoSacado = 123;
		//Arrumar
		detalhe400.numeroInscricaoSacado = 231;
		//Arrumar
		detalhe400.nomeDoSacado = "Lazaro ";
		//Arrumar
		detalhe400.endereco = "Arrumar";
		//Arrumar
		detalhe400.bairro = "Leste";
		//Arrumar
		detalhe400.cep = 72154255;
		//Arrumar
		detalhe400.praca = "";
		//Arrumar
		detalhe400.estado = "DF";
		//Arrumar
		detalhe400.sacadorOuAnalistaOuMensagem = "";
		//Arrumar
		detalhe400.prazo = 123;
		
		detalhe400.moeda = 9;
		//Arrumar
		detalhe400.numeroSequencial = 123;
		
		linha = ArquivoFabrica.gerarLinha(detalhe400, TipoArquivo.CNAB400);
		
		return linha;
		
	}

	/**
	 * Calcula o digito verificador do Nosso Numero
	 */
	@Override
	public int calculaDVNossoNumero(long numero) {
		String nossoNumero = String.format("%010d", numero);
		int dv = 0;
		String pesos = "5432765432";
		int soma = 0;
		
		if (nossoNumero.length() != 10) {
			throw new CobrancaExcecao("Tamanho de número inválido para calculo do digito verificador.");
		}
		
		for (int i = 0; i < nossoNumero.length(); i++) {
			soma += Integer.valueOf(nossoNumero.substring(i, i+1)) * Integer.valueOf(pesos.substring(i, i+1));  
		}
		
		int resto = soma % 11;
		
		if (resto == 0 || resto == 1)
			dv = 0;
		else
			dv = 11 - resto;
		
		return dv;
	}

	/**
	 * Retorna um objeto Boleto com todos os dados do cedente e sacados devidamente informados.
	 */
	@Override
	public Boleto obterBoleto(Cedente cedente, Date dataVencimento, double valorBoleto, 
			long numeroDocumento, Sacado sacado, String nossoNumero) {
		
		Boleto boleto = new Boleto();
		
		boleto.setDataVencimento(dataVencimento);
		boleto.setCedente(cedente);
		boleto.setDataEmissao(new Date());
		boleto.setValorBoleto(valorBoleto);
		boleto.setValorDesconto(0);
		boleto.setCarteira(cedente.getCarteira());
		boleto.setNumeroDocumento(numeroDocumento);
		boleto.setEspecieTitulo(EspecieTitulo.PD);
		boleto.setIdentificacaoAceite("NÃO");
		boleto.setSacado(sacado);
		//boleto.setSacadorAvalista(sacadorAvalista);
		boleto.setLocalPagamento(cedente.getLocalPagamento());
		boleto.setInstrucaoSacado(cedente.getInstrucaoSacado());
		boleto.setInstrucao1("Após vencimento, cobrar multa de 10% + juros de mora de 1% a.m.");
		boleto.setInstrucao2("Cobrar honorários advocatícios de 20% a cada 15 dias de atraso.");
		boleto.setInstrucao3("");
		boleto.setInstrucao4("");
		boleto.setInstrucao5("");
		boleto.setInstrucao6("");
		boleto.setInstrucao7("");
		//boleto.setInstrucao8("Contrato: 5843534");
		boleto.setNossoNumero(nossoNumero);

		CampoLivre campoLivre = CampoLivreFabrica.create(boleto);
		boleto.setCampoLivre(campoLivre);
		logger.info("Campo Livre: " + campoLivre.write());
		
		CodigoBarras codigoBarras = new CodigoBarras(boleto, boleto.getCampoLivre());
		boleto.setCodigoBarras(codigoBarras);
		logger.info("Codigo de Barras: " + codigoBarras.write());
		
		LinhaDigitavel linhaDigitavel = new LinhaDigitavel(codigoBarras);
		boleto.setLinhaDigitavel(linhaDigitavel);
		logger.info("Linha Digitavel: " + linhaDigitavel.write());
		
		return boleto;
	}
	
	/**
	 * Calcula o nome do arquivo para geração de remessa.
	 * @return
	 */
	public String calcularNomeArquivoRemessa(Long codigoCedente) {
		return calcularNomeArquivoRemessa(codigoCedente, "HSBC");
	}

}
