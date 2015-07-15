package br.com.infosolo.cobranca.negocio;

import java.util.Date;

import br.com.infosolo.cobranca.boleto.CodigoBarras;
import br.com.infosolo.cobranca.boleto.LinhaDigitavel;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivre;
import br.com.infosolo.cobranca.boleto.campolivre.CampoLivreFabrica;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoLote;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoP;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoQ;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeLoteRemessa;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.dominio.boleto.Sacado;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.EspecieTitulo;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;
import br.com.infosolo.cobranca.enumeracao.TipoInscricao;
import br.com.infosolo.cobranca.enumeracao.TipoMoeda;
import br.com.infosolo.cobranca.enumeracao.TipoMovimento;
import br.com.infosolo.cobranca.enumeracao.TipoOcorrencia;
import br.com.infosolo.cobranca.enumeracao.TipoRegistro;
import br.com.infosolo.cobranca.util.ArquivoFabrica;
import br.com.infosolo.cobranca.util.Constantes;
import br.com.infosolo.comum.util.DataUtil;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;

public class BancoBrasilNegocio extends BancoNegocio {
	
	private static Logger logger = new Logger(BancoBrasilNegocio.class);
	
	public BancoBrasilNegocio() {
		banco = Banco.BANCO_BRASIL;
	}
	
	/**
	 * Retorna o string de cabecalho do arquivo de remessa CNAB240.
	 */
	public String retornarCabecalhoArquivoRemesaCNAB240(Cedente cedente) {
		String linha = "";
		Date dataAtual = new Date();
		String codigoConvenio = String.format("%013d", new Long(cedente.getConvenio())); 
		
		RegistroCabecalhoArquivo cabecalhoArquivo = new RegistroCabecalhoArquivo();
		cabecalhoArquivo.banco = banco.getCodigo();
		cabecalhoArquivo.tipoRegistro = TipoRegistro.HEADER_ARQUIVO.getValue();
		cabecalhoArquivo.nomeBanco = banco.getNome();
		if (cedente.getCpfCnpj().length() <= 11) {
			cabecalhoArquivo.tipoInscricaoEmpresa = TipoInscricao.CPF.getValue();
		}
		else {
			cabecalhoArquivo.tipoInscricaoEmpresa = TipoInscricao.CNPJ.getValue();
		}
		cabecalhoArquivo.numeroInscricaoEmpresa = Long.parseLong(cedente.getCpfCnpj());
		cabecalhoArquivo.codigoConvenioBanco = String.format("COBCNAB%13s", codigoConvenio);
		cabecalhoArquivo.agenciaMantenedoraConta = new Long(cedente.getContaBancaria().getAgencia());
		cabecalhoArquivo.digitoVerificadorAgencia = cedente.getContaBancaria().getDigitoAgencia().charAt(0);
		cabecalhoArquivo.numeroContaCorrente = new Long(cedente.getContaBancaria().getConta());
		cabecalhoArquivo.digitoVerificadorConta = cedente.getContaBancaria().getDigitoConta().charAt(0);
		cabecalhoArquivo.digitoVerificadorAgenciaConta = '0';
		cabecalhoArquivo.nomeEmpresa = cedente.getNome(); 
		cabecalhoArquivo.codigoArquivo = TipoMovimento.REMESSA.getValue();
		cabecalhoArquivo.dataGeracaoArquivo = DataUtil.formatarDataArquivo(dataAtual);
		cabecalhoArquivo.horaGeracaoArquivo = DataUtil.formatarHoraArquivo(dataAtual);
		cabecalhoArquivo.numeroSequencialArquivo = 1;
		
		cabecalhoArquivo.versaoLayoutArquivo = "084";
		cabecalhoArquivo.duplicNaoAceitas = 'N';
		cabecalhoArquivo.liberacaoAutomatica = 'N';
		
		linha = ArquivoFabrica.gerarLinha(cabecalhoArquivo, TipoArquivo.CNAB240);
		
		return linha;
	}
	
	/**
	 * Retorna o string de cabecalho de lote de remessa CNAB240.
	 */
	public String retornarCabecalhoLoteRemesaCNAB240(Cedente cedente) {
		String linha = "";
		String codigoConvenio = String.format("%013d", new Long(cedente.getConvenio())); 
		
		RegistroCabecalhoLote cabecalhoLote = new RegistroCabecalhoLote();
		cabecalhoLote.banco = banco.getCodigo();
		cabecalhoLote.lote = 1;
		cabecalhoLote.tipoRegistro = TipoRegistro.HEADER_LOTE.getValue();
		
		// Tipo de Operacao: R - Remessa, T - Retorno, O - Retorno operacao (somente para desconto)
		cabecalhoLote.tipoOperacao = 'R';
		
		// Tipo de Servico: 01 - Cobranca, 09 - Desconto, 11 - Conciliacao mensal
		cabecalhoLote.tipoServico = "01";
		
		cabecalhoLote.layoutLote = "043";
		
		// Forma de Lancamento
		cabecalhoLote.reservadoCNAB12 = "00";
		
		cabecalhoLote.tipoInscricaoEmpresa = (cedente.getCpfCnpj().length() <= 11) ? TipoInscricao.CPF.getValue() : TipoInscricao.CNPJ.getValue();
		cabecalhoLote.numeroInscricaoEmpresa = Long.parseLong(cedente.getCpfCnpj());
		cabecalhoLote.codigoContratoCobranca = String.format("COB    %13s", codigoConvenio);
		cabecalhoLote.agenciaMantenedoraConta = new Long(cedente.getContaBancaria().getAgencia());
		cabecalhoLote.digitoVerificadorAgencia = cedente.getContaBancaria().getDigitoAgencia().charAt(0);
		cabecalhoLote.numeroContaCorrente = new Long(cedente.getContaBancaria().getConta());
		cabecalhoLote.digitoVerificadorConta = cedente.getContaBancaria().getDigitoConta().charAt(0);
		cabecalhoLote.digitoVerificadorAgenciaConta = '0';
		cabecalhoLote.nomeEmpresa = cedente.getNome(); 
		
		linha = ArquivoFabrica.gerarLinha(cabecalhoLote, TipoArquivo.CNAB240);
		
		return linha;
	}

	/**
	 * Retorna o string de rodape de lote de remessa CNAB240.
	 */
	public String retornarRodapeLoteRemesaCNAB240(int qtdeRegistros) {
		String linha = "";
		
		RegistroRodapeLoteRemessa rodapeLote = new RegistroRodapeLoteRemessa();
		rodapeLote.banco = banco.getCodigo();
		rodapeLote.lote = 1;
		rodapeLote.tipoRegistro = TipoRegistro.TRAILER_LOTE.getValue();
		rodapeLote.qtdeRegistros = qtdeRegistros;
		rodapeLote.valorSomatoria = 0;

		linha = ArquivoFabrica.gerarLinha(rodapeLote, TipoArquivo.CNAB240);

		return linha;
	}

	/**
	 * Retorna o string de rodape de arquivo de remessa CNAB240.
	 */
	public String retornarRodapeArquivoRemesaCNAB240(int qtdeRegistros) {
		String linha = "";
		
		RegistroRodapeArquivo rodapeArquivo = new RegistroRodapeArquivo();
		rodapeArquivo.banco = banco.getCodigo();
		rodapeArquivo.lote = 9999;
		rodapeArquivo.tipoRegistro = TipoRegistro.TRAILER_ARQUIVO.getValue();
		rodapeArquivo.qtdeLotes = 1;
		rodapeArquivo.qtdeRegistros = qtdeRegistros;

		linha = ArquivoFabrica.gerarLinha(rodapeArquivo, TipoArquivo.CNAB240);

		return linha;
	}
	
	/**
	 * Retorna o string de detalhe tipo segmento P de remessa CNAB240.
	 */
	public String retornarDetalheSegmentoPRemessa(int numeroRegistro, Cedente cedente, Boleto boleto) {
		String linha = "";
		
		RegistroDetalheSegmentoP registroDetalhe = new RegistroDetalheSegmentoP();
		registroDetalhe.banco = banco.getCodigo();
		registroDetalhe.lote = 1;
		registroDetalhe.tipoRegistro = TipoRegistro.DETALHE.getValue();
		registroDetalhe.numeroRegistro = numeroRegistro;
		registroDetalhe.segmento = 'P';
		registroDetalhe.codigoMovimento = TipoOcorrencia.REMESSA_REGISTRAR.getCodigo();
		registroDetalhe.agenciaMantenedoraConta = new Long(cedente.getContaBancaria().getAgencia());
		registroDetalhe.digitoVerificadorAgencia = cedente.getContaBancaria().getDigitoAgencia().charAt(0);
		registroDetalhe.numeroContaCorrente = new Long(cedente.getContaBancaria().getConta());
		registroDetalhe.digitoVerificadorConta = cedente.getContaBancaria().getDigitoConta().charAt(0);
		registroDetalhe.nossoNumero = boleto.getNossoNumero();
		registroDetalhe.codigoCarteira = Constantes.CARTEIRA_COBRANCA_SIMPLES;
		registroDetalhe.tipoCadastramento = 1;
		registroDetalhe.tipoDocumento = 1;
		// Para operacoes de Desconto de Duplicatas este campo sera
		// desprezado, ou seja, a emissao do bloqueto sera feita pelo Banco 
		registroDetalhe.identificacaoBloqueto = Constantes.EMISSAO_BLOQUETO_CLIENTE_EMITE;
		registroDetalhe.identificacaoDistribuicao = Constantes.DISTRIBUICAO_BLOQUETO_CLIENTE_DISTRIBUI;
		registroDetalhe.numeroDocumento = String.valueOf(boleto.getNumeroDocumento());
		registroDetalhe.dataVencimento = DataUtil.formatarDataArquivo(boleto.getDataVencimento());
		registroDetalhe.valorTitulo = boleto.getValorBoleto();
		registroDetalhe.agenciaCobradora = 0;
		registroDetalhe.digitoAgenciaCobradora = ' ';
		registroDetalhe.especieTitulo = boleto.getEspecieTitulo().getValor();
		registroDetalhe.aceite = boleto.getIdentificacaoAceite().charAt(0);
		registroDetalhe.dataEmissao = DataUtil.formatarDataArquivo(boleto.getDataEmissao());
		if (boleto.getValorJurosMora() > 0) {
			registroDetalhe.codigoJurosMora = Constantes.CODIGO_MORA_VALOR_POR_DIA;
			registroDetalhe.dataJurosMora = DataUtil.formatarDataArquivo(boleto.getDataVencimento());
			registroDetalhe.valorJurosMora = boleto.getValorJurosMora();
		}
		else {
			registroDetalhe.codigoJurosMora = Constantes.CODIGO_MORA_ISENTO;
			registroDetalhe.dataJurosMora = DataUtil.DATA_NULA_ARQUIVO;
			registroDetalhe.valorJurosMora = 0;
		}
		if (boleto.getValorDesconto() > 0) {
			registroDetalhe.codigoDesconto = Constantes.CODIGO_DESCONTO_VALOR_FIXO;
			registroDetalhe.dataDesconto = DataUtil.formatarDataArquivo(boleto.getDataVencimento());
			registroDetalhe.valorDesconto = boleto.getValorDesconto();
		}
		else {
			registroDetalhe.codigoDesconto = 0;
			registroDetalhe.dataDesconto = DataUtil.DATA_NULA_ARQUIVO;
			registroDetalhe.valorDesconto = 0;
		}
		registroDetalhe.valorIOF = 0;
		registroDetalhe.valorAbatimento = 0;
		registroDetalhe.usoEmpresaCedente = String.valueOf(boleto.getNumeroDocumento());
		if (boleto.getQuantidadeDiasProtesto() > 0) {
			registroDetalhe.codigoProtesto = Constantes.CODIGO_PROTESTO_DIAS_CORRIDOS;
			registroDetalhe.numeroDiasProtesto = boleto.getQuantidadeDiasProtesto();
		}
		else {
			registroDetalhe.codigoProtesto = Constantes.CODIGO_PROTESTO_NAO_PROTESTAR;
			registroDetalhe.numeroDiasProtesto = 0;
		}
		registroDetalhe.codigoBaixa = Constantes.CODIGO_BAIXA_NAO_BAIXAR_DEVOLVER;
		registroDetalhe.numeroDiasBaixa = 0;
		registroDetalhe.codigoMoeda = TipoMoeda.REAL.getCodigo();
		registroDetalhe.numeroContrato = 0;
		
		linha = ArquivoFabrica.gerarLinha(registroDetalhe, TipoArquivo.CNAB240);

		return linha;
	}

	/**
	 * Retorna o string de detalhe tipo segmento Q de remessa CNAB240.
	 */
	public String retornarDetalheSegmentoQRemessa(int numeroRegistro, Boleto boleto) {
		String linha = "";
		
		String endereco = "";
		if (boleto.getSacado().getEndereco().getLogradouro() != null)
			endereco += boleto.getSacado().getEndereco().getLogradouro();
		if (boleto.getSacado().getEndereco().getLogradouro() != null && !boleto.getSacado().getEndereco().getLogradouro().isEmpty())
			endereco += " "; 
		if (boleto.getSacado().getEndereco().getEndereco() != null)
			endereco += boleto.getSacado().getEndereco().getEndereco(); 
			
		RegistroDetalheSegmentoQ registroDetalhe = new RegistroDetalheSegmentoQ();
		registroDetalhe.banco = banco.getCodigo();
		registroDetalhe.lote = 1;
		registroDetalhe.tipoRegistro = TipoRegistro.DETALHE.getValue();
		registroDetalhe.numeroRegistro = numeroRegistro;
		registroDetalhe.segmento = 'Q';
		registroDetalhe.codigoMovimento = TipoOcorrencia.REMESSA_REGISTRAR.getCodigo();
		registroDetalhe.tipoInscricao = (boleto.getSacado().getCpfCnpj().length() <= 11) ? TipoInscricao.CPF.getValue() : TipoInscricao.CNPJ.getValue();
		registroDetalhe.numeroInscricao = new Long(boleto.getSacado().getCpfCnpj());	
		registroDetalhe.nome = boleto.getSacado().getNome();
		registroDetalhe.endereco = endereco;
		registroDetalhe.bairro = boleto.getSacado().getEndereco().getBairro();
		registroDetalhe.cep = Long.parseLong(TextoUtil.retiraSimbolos(boleto.getSacado().getEndereco().getCep()));
		registroDetalhe.cidade = boleto.getSacado().getEndereco().getCidade();
		registroDetalhe.estado = boleto.getSacado().getEndereco().getUf();
		registroDetalhe.tipoInscricaoAvalista = 0;
		registroDetalhe.numeroInscricaoAvalista = 0;
		registroDetalhe.nomeAvalista = "";
		
		linha = ArquivoFabrica.gerarLinha(registroDetalhe, TipoArquivo.CNAB240);

		return linha;
	}

	/**
	 * Retorna um objeto Boleto com todos os dados do cedente e sacados devidamente informados.
	 */
	@Override
	public Boleto obterBoleto(Cedente cedente, Date dataVencimento, double valorBoleto, long numeroDocumento, Sacado sacado, String nossoNumero) {
		
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
		boleto.setInstrucao2("INSTRUCAO 2");
		boleto.setInstrucao3("INSTRUCAO 3");
		boleto.setInstrucao4("INSTRUCAO 4");
		boleto.setInstrucao5("INSTRUCAO 5");
		boleto.setInstrucao6("INSTRUCAO 6");
		boleto.setInstrucao7("INSTRUCAO 7");
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
	 * Formata o Nosso Numero com Digito Verificador
	 * @return
	 */
	@Override
	public String formatarNossoNumero(long codigoCedente, long sequencial, int tamanho) {
		return this.formatarNossoNumeroBB(codigoCedente, sequencial, tamanho);
	}

}
