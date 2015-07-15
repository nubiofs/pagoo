package br.com.infosolo.cobranca.negocio.ejb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.infosolo.cobranca.boleto.GeradorBoleto;
import br.com.infosolo.cobranca.boleto.guia.GeradorGuia;
import br.com.infosolo.cobranca.boleto.guia.Guia;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.dto.BancoDTO;
import br.com.infosolo.cobranca.dominio.dto.BoletoDTO;
import br.com.infosolo.cobranca.dominio.dto.CedenteDTO;
import br.com.infosolo.cobranca.dominio.dto.EnderecoDTO;
import br.com.infosolo.cobranca.dominio.dto.PesquisaBoletoDTO;
import br.com.infosolo.cobranca.dominio.dto.SacadoDTO;
import br.com.infosolo.cobranca.dominio.dto.SacadorAvalistaDTO;
import br.com.infosolo.cobranca.dominio.entidades.ArquivoRemessaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BancoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidadePK;
import br.com.infosolo.cobranca.dominio.entidades.CobrancaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.EnderecoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.EspecieTituloEntidade;
import br.com.infosolo.cobranca.dominio.entidades.SacadoEntidade;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.EspecieTitulo;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.negocio.BancoNegocio;
import br.com.infosolo.cobranca.util.CobrancaProperties;
import br.com.infosolo.cobranca.util.ConstantesErros;
import br.com.infosolo.cobranca.util.DominioUtil;
import br.com.infosolo.comum.util.ArquivoUtil;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;

/**
 * Session Bean implementation class CobrancaBancariaEJBBean
 */
@Stateless
public class CobrancaBancariaNegocio implements CobrancaBancariaNegocioLocal {
	private static final Logger logger = new Logger(CobrancaBancariaNegocio.class);

	private static final String CONVENIO_AL = "041330";
	
	@PersistenceContext(unitName = "infosolo-cobraca")
	private EntityManager em;

	@EJB
	private EmailNegocioLocal emailNegocio;

	@EJB
	private ArquivoNegocioLocal arquivoNegocio;

	private static URL TEMPLATE_BOLETO = CobrancaBancariaNegocio.class.getResource("/recursos/templates/BoletoTemplateSemSacadorAvalista.pdf");
	private static URL TEMPLATE_GUIA = CobrancaBancariaNegocio.class.getResource("/recursos/templates/SNRGuiaTemplatePadrao1.pdf");
	private static URL TEMPLATE_GUIA_SIMPLES = CobrancaBancariaNegocio.class.getResource("/recursos/templates/SNRGuiaTemplatePadrao2.pdf");
	private static URL TEMPLATE_GUIA_DUPLA = CobrancaBancariaNegocio.class.getResource("/recursos/templates/SNRGuiaTemplatePadrao3.pdf");
	private static URL TEMPLATE_GUIA_SIMPLES_IMPOSTOS = CobrancaBancariaNegocio.class.getResource("/recursos/templates/SNRGuiaTemplatePadrao4.pdf");
	
	private static String DIRETORIO_ARQUIVOS_BOLETO_PROPERTIE = "diretorio_arquivos_boleto";
	
	private CobrancaProperties properties = new CobrancaProperties();

	/**
	 * TODO 		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
	 * Default constructor.
	 */
	public CobrancaBancariaNegocio() {
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");
		logger.info("NECESSARIO AINDA ALTERAR BIGINT E BIGSERIAL COLUNAS PKS DAS TABELAS E TAMBEM ALTERAR MAPEAMENTO PK SEQUENCE GENERATOR SISTEMA COBRANCA!!");		
	}

	/**
	 * Retorna uma entidade de cedente
	 * @param cedenteDTO
	 * @return
	 */
	private CedenteEntidade retornarCedenteEntidade(CedenteDTO cedenteDTO) {
		CedenteEntidadePK cedentePK = new CedenteEntidadePK();
		cedentePK.setCodigoConvenio(cedenteDTO.getCodigoConvenio());
		cedentePK.setNumeroCpfCnpjCedente(cedenteDTO.getCpfCnpjCedente());

		CedenteEntidade cedenteEntidade = em.find(CedenteEntidade.class, cedentePK);
		
		// Se nao encontrar o cedente, emite excecao.
		if (cedenteEntidade == null) { 
			throw new CobrancaExcecao(MessageFormat.format(ConstantesErros.ERROR_CEDENTE_NAO_ENCONTRADO, cedenteDTO.getCpfCnpjCedente(), cedenteDTO.getCodigoConvenio()));
		}
		return cedenteEntidade;
	}

	/**
	 * Gera um boleto de cobranca. 
	 * Retorna o mesmo DTO com o campo nosso numero preenchido.
	 * @param boletoDTO
	 * @return
	 */
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public BoletoDTO gerarBoletoCobranca(BoletoDTO boletoDTO) {
		BoletoEntidade boletoEntidade = null;
		CedenteDTO cedenteDTO = boletoDTO.getCedente();
		CedenteEntidade cedenteEntidade = retornarCedenteEntidade(cedenteDTO);

		if (boletoDTO.getInstrucoesBancarias() == null) {
			boletoDTO.setInstrucoesBancarias(cedenteDTO.getInstrucoes());
		}
		
		if (boletoDTO.getDataEmissao() == null) {
			boletoDTO.setDataEmissao(new Date());
		}
		
		if (boletoDTO.getNossoNumero() != null && !boletoDTO.getNossoNumero().equals("")) {
			// Acontece quando quer regerar o boleto existente com data ou valor alterados
			boletoEntidade = recuperarBoletoPeloNossoNumero(boletoDTO.getNossoNumero(), true);
			if (boletoEntidade != null) {
				atualizaDadosBoleto(boletoDTO, boletoEntidade);
			}
		}
		else if (boletoDTO.getIdBoleto() == null || boletoDTO.getIdBoleto().longValue() == 0)
			// Acontece quando o boleto nao existe na base
			boletoEntidade = retornarNovoBoletoEntidade(boletoDTO, cedenteEntidade, null);
		else {
			// Acontece quando quer simplesmente regerar o boleto otendo um novo nosso numero
			boletoEntidade = em.find(BoletoEntidade.class, boletoDTO.getIdBoleto());
		}
		
		if (boletoEntidade != null) {
			if (boletoDTO.getNossoNumero() == null || boletoDTO.getNossoNumero().equals("")) {
				gerarNossoNumero(boletoEntidade, cedenteEntidade, false);
			}
			
			/**
			 * Gera��o do PDF
			 */
			logger.info("Gerando Stream de PDF para Nosso Numero: " + boletoEntidade.getNossoNumero());
			
			Boleto boleto = DominioUtil.retornarDominioBoleto(boletoEntidade);
			
			GeradorBoleto geradorBoleto = new GeradorBoleto(boleto);
			
			byte[] byteArray = geradorBoleto.getPdfAsByteArray();
			
			boletoEntidade.setDataEmissao(boletoDTO.getDataEmissao());
			
			boletoEntidade.setInstrucoesBancarias(boletoDTO.getInstrucoesBancarias());
			
			Long codigoConvenio = boletoEntidade.getCedente().getId().getCodigoConvenio(); 
			
			// Alteracao para gerar arquivo em disco
			File file = gravaBoletoEmDisco(byteArray, String.valueOf(codigoConvenio), boletoEntidade.getNossoNumero(), null);
			
			if (file == null) {
				// Se nao consegui gravar no disco gravo no campo BLOB
				boletoEntidade.setBoletoFisico(byteArray);
			}
			
			em.persist(boletoEntidade);
	
			em.flush();
			
			// Atualiza dados de retorno do boleto
			boletoDTO.setIdBoleto(boletoEntidade.getIdBoleto());
			
			boletoDTO.setNossoNumero(boletoEntidade.getNossoNumero());
			
			boletoDTO.setNumeroDocumento(boletoEntidade.getNumeroDocumento());
		}
		else {
			logger.error("Nao pude obter a entidade de boleto.");
		}
		return boletoDTO;
	}
	
	
	/**
	 * Registra a cobrança bancária. É necessário passar a lista de boletos devidamente preenchida com todos os dados.
	 */
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public List<BoletoDTO> registrarCobrancaBancaria(List<BoletoDTO> listaBoletosDTO) {
		CedenteEntidade cedenteEntidade = null;
		CobrancaEntidade cobrancaEntidade = new CobrancaEntidade();
		List<BoletoEntidade> listaBoletosEntidade = new ArrayList<BoletoEntidade>();
		
		em.persist(cobrancaEntidade);
		//int i = 2;
		
		// Monta array de BoletoEntidade
		for (BoletoDTO boleto : listaBoletosDTO) {
			if (cedenteEntidade == null) { 
				CedenteDTO cedenteDTO = boleto.getCedente();
				cedenteEntidade = retornarCedenteEntidade(cedenteDTO);
			}
			
			BoletoEntidade boletoEntidade = recuperarBoletoPeloNossoNumero(boleto.getNossoNumero(), false);
			
			if (boletoEntidade != null) {
				logger.info("Obtendo boleto " + boleto.getNossoNumero());
				boletoEntidade.setValorAbatimento(boleto.getValorAbatimento() != null ? new BigDecimal(boleto.getValorAbatimento()) : new BigDecimal(0));
				boletoEntidade.setValorBoleto(boleto.getValorBoleto() != null ? new BigDecimal(boleto.getValorBoleto()) : new BigDecimal(0));
				boletoEntidade.setValorDesconto(boleto.getValorDesconto() != null ? new BigDecimal(boleto.getValorDesconto()) : new BigDecimal(0));
				boletoEntidade.setValorIof(boleto.getValorIof() != null ? new BigDecimal(boleto.getValorIof()) : new BigDecimal(0));
				boletoEntidade.setValorJurosMora(boleto.getValorJurosMora() != null ? new BigDecimal(boleto.getValorJurosMora()) : new BigDecimal(0));
				boletoEntidade.setValorMulta(boleto.getValorMulta() != null ? new BigDecimal(boleto.getValorMulta()) : new BigDecimal(0));
				boletoEntidade.setAssuntoEmail(boleto.getAssuntoEmail());
				boletoEntidade.setTextoEmail(boleto.getTextoEmail());
				boletoEntidade.setAnexoPlanilhaEmail(boleto.getAnexoPlanilhaEmail());
				boletoEntidade.setAnexoRelatorioEmail(boleto.getAnexoRelatorioEmail());

				String tokenAcessoEmail = TextoUtil.gerarTextoUUID();
				boletoEntidade.setTokenAcessoEmail(tokenAcessoEmail);
				logger.info("Token de download gerado: " + tokenAcessoEmail);
					
				em.persist(boletoEntidade);
				
				listaBoletosEntidade.add(boletoEntidade); // adiciona boleto a lista que sera pesistida.
			}
			
			//if (--i == 0) break;
		}

		ArquivoRemessaEntidade arquivoEntidade = new ArquivoRemessaEntidade();
		cobrancaEntidade.setArquivoRemessa(arquivoEntidade);
		cobrancaEntidade.setBoletos(listaBoletosEntidade);
		
		em.persist(arquivoEntidade);
		em.persist(cobrancaEntidade);

		/**
		 * Envia email
		 */
		logger.info("Iniciando envio de email...");
		for (BoletoEntidade boletoEntidade : listaBoletosEntidade) {
			enviarEmail(boletoEntidade);
		}
		logger.info("Envio de email terminado.");
		
		// Gera os arquivos de remessa
		List<Boleto> listaBoletos = arquivoNegocio.gerarArquivoRemessa(cobrancaEntidade, cedenteEntidade, 
				listaBoletosEntidade, arquivoEntidade);
		
		// Atualiza os boletos
		for (Boleto boleto : listaBoletos) {
			for (BoletoEntidade boletoEntidade : listaBoletosEntidade) {
				if (boletoEntidade.getNumeroDocumento().equals(String.valueOf(boleto.getNumeroDocumento()))) {
					boletoEntidade.setCobranca(cobrancaEntidade);

					EspecieTituloEntidade especieTituloEntidade = em.find(EspecieTituloEntidade.class, new Long(boleto.getEspecieTitulo().getValor()));
					boletoEntidade.setEspecieTitulo(especieTituloEntidade);
						
					em.persist(boletoEntidade);
					break;
				}				
			}
		}
		
		em.persist(arquivoEntidade);
		em.persist(cobrancaEntidade);
		
		em.flush();
		
		/**
		 * Salva em disco
		 */
		//GeradorBoleto geradorBoleto = new GeradorBoleto();
		//geradorBoleto.getListaArquivosPDF("C:/TEMP", listaBoletos);		
		
		logger.info("Registro da cobranca bancaria finalizado!");
		
		return listaBoletosDTO;
	}
	
	/**
	 * Envia o email para os destinatarios
	 * @param boleto
	 * @param boletoEntidade
	 */
	private boolean enviarEmail(BoletoEntidade boletoEntidade) {
		String serverName = getRemoteServerName();
		String listaEmail = "";
		
		logger.info("Iniciando envio de email...");

		for (EnderecoEntidade enderecoEntidade : boletoEntidade.getSacado().getEnderecos()) {
			if (listaEmail.length() > 0) listaEmail = listaEmail + ";";
			if (enderecoEntidade.getEmail() != null) {
				logger.info("-> Adicionando " + enderecoEntidade.getEmail());
				listaEmail = listaEmail + enderecoEntidade.getEmail();
			}
		}
		
		// TODO: Retirar linha abaixo em producao
		//listaEmail = "";
		
		if (listaEmail.length() == 0) {
			logger.error("Não foi encontrado destinatários de email! Enviando para mail da empresa.");
			listaEmail = properties.getProperty("empresa.mail");
		}

		if (listaEmail.indexOf("leandro.lima@infosolo.com.br") < 0) listaEmail += ";leandro.lima@infosolo.com.br";
		if (listaEmail.indexOf("david.reis@fdl.srv.br") < 0) listaEmail += ";david.reis@fdl.srv.br";
		if (listaEmail.indexOf("andre.ianelli@infosolo.com.br") < 0) listaEmail += ";andre.ianelli@infosolo.com.br";
		
		String textoEmail = boletoEntidade.getTextoEmail();
		
		if (textoEmail != null) {
			String linkDownload = "http://" + serverName  + "/infosolo-cobranca-web/BoletoServlet/" + boletoEntidade.getTokenAcessoEmail();
			
			textoEmail = textoEmail.replaceAll("@link_boleto", boletoEntidade.getBoletoFisico() != null ? linkDownload.concat("/Boleto.pdf") : "");
			textoEmail = textoEmail.replaceAll("@link_relatorio", boletoEntidade.getAnexoRelatorioEmail() != null ? linkDownload.concat("/Relatorio.pdf") : "");
			textoEmail = textoEmail.replaceAll("@link_planilha", boletoEntidade.getAnexoPlanilhaEmail() != null ? linkDownload.concat("/Relatorio.csv") : "");
			
			logger.info("-> Enviando para " + listaEmail);
			logger.info("-> URL de download: " + linkDownload);

			String assunto = boletoEntidade.getAssuntoEmail();
			assunto += " (" + boletoEntidade.getNossoNumero() + ")";
			
			return emailNegocio.enviarMensagemEmail(listaEmail.split(";"), assunto, textoEmail,
					boletoEntidade.getBoletoFisico(), boletoEntidade.getAnexoRelatorioEmail(), boletoEntidade.getAnexoPlanilhaEmail());
		}
		logger.info("Envio de email terminado.");
		
		return false;
	}
	

	/**
	 * Retorna o endereco do servidor de aplicação.
	 * @return
	 */
	private String getRemoteServerName() {
		String localhost = "localhost:8080";
		
		try {
			//InetAddress thisIp = InetAddress.getLocalHost();
			//localhost = thisIp.getHostAddress();
			localhost = properties.getString("server.remote.address");
		}
		catch(Exception e) {
			logger.error("Nao foi possivel obter endereco IP: " + e.toString());
		}
		return localhost;
	}
	
	private void atualizaDadosBoleto(BoletoDTO boleto, BoletoEntidade boletoEntidade) {
		boletoEntidade.setDataEmissao(boleto.getDataEmissao());
		java.util.Date dataProcessamento = new java.util.Date();
		boletoEntidade.setDataProcessamento(new Timestamp(dataProcessamento.getTime()));
		boletoEntidade.setDataVencimento(boleto.getDataVencimento());

		EspecieTituloEntidade especieEntidade = em.find(EspecieTituloEntidade.class, new Long(EspecieTitulo.PD.getValor()));
		boletoEntidade.setEspecieTitulo(especieEntidade);
		
		boletoEntidade.setNumeroDocumento(boleto.getNumeroDocumento());
		boletoEntidade.setValorAbatimento(boleto.getValorAbatimento() != null ? new BigDecimal(boleto.getValorAbatimento()) : new BigDecimal(0));
		boletoEntidade.setValorBoleto(boleto.getValorBoleto() != null ? new BigDecimal(boleto.getValorBoleto()) : new BigDecimal(0));
		boletoEntidade.setValorDesconto(boleto.getValorDesconto() != null ? new BigDecimal(boleto.getValorDesconto()) : new BigDecimal(0));
		boletoEntidade.setValorIof(boleto.getValorIof() != null ? new BigDecimal(boleto.getValorIof()) : new BigDecimal(0));
		boletoEntidade.setValorJurosMora(boleto.getValorJurosMora() != null ? new BigDecimal(boleto.getValorJurosMora()) : new BigDecimal(0));
		boletoEntidade.setValorMulta(boleto.getValorMulta() != null ? new BigDecimal(boleto.getValorMulta()) : new BigDecimal(0));
		boletoEntidade.setAssuntoEmail(boleto.getAssuntoEmail());
		boletoEntidade.setTextoEmail(boleto.getTextoEmail());
		boletoEntidade.setAnexoPlanilhaEmail(boleto.getAnexoPlanilhaEmail());
		boletoEntidade.setAnexoRelatorioEmail(boleto.getAnexoRelatorioEmail());
	}
	
	/**
	 * Retorna a entidade de boleto pelo DTO informado.
	 * @param boleto
	 * @param cedenteEntidade
	 * @param cobrancaEntidade
	 * @return
	 */
	public BoletoEntidade retornarNovoBoletoEntidade(BoletoDTO boleto,
			CedenteEntidade cedenteEntidade, CobrancaEntidade cobrancaEntidade) {
		
		BoletoEntidade boletoEntidade = new BoletoEntidade();
		boletoEntidade.setCedente(cedenteEntidade);
		boletoEntidade.setCobranca(cobrancaEntidade);
		SacadoEntidade sacadoEntidade = null;
		
		if (boleto.getSacado() != null) { // Se vier o sacado no BoletoDTO
			sacadoEntidade = em.find(SacadoEntidade.class, boleto.getSacado().getCpfCnpjSacado());
			
			// Se o sacado for nulo é porque ele ainda não existe no banco e deve ser incluído.
			if (sacadoEntidade == null) { 
				sacadoEntidade = new SacadoEntidade();
			}
			sacadoEntidade.setNome(boleto.getSacado().getNome());
			sacadoEntidade.setNumeroCpfCnpjSacado(boleto.getSacado().getCpfCnpjSacado());
			em.persist(sacadoEntidade); 
			
			// Configurando endereco.
			List<EnderecoDTO> enderecos = boleto.getSacado().getEnderecos();
			List<EnderecoEntidade> enderecosEntidade = new ArrayList<EnderecoEntidade>();
			
			for (EnderecoDTO endereco : enderecos) { // adicionando os endereços.
				//EnderecoEntidade enderecoEntidade = retornarEnderecoSacadoPorCep(sacadoEntidade.getNumeroCpfCnpjSacado(), endereco.getCep());
				EnderecoEntidade enderecoEntidade = retornarEnderecoSacadoPorEmail(sacadoEntidade.getNumeroCpfCnpjSacado(), endereco.getEmail());

				if (enderecoEntidade == null) {
					enderecoEntidade = new EnderecoEntidade();
				}
				enderecoEntidade.setBairro(endereco.getBairro());
				enderecoEntidade.setCep(endereco.getCep());
				enderecoEntidade.setCidade(endereco.getCidade());
				enderecoEntidade.setComplemento(endereco.getComplemento());
				enderecoEntidade.setDescricao(endereco.getDescricao());
				enderecoEntidade.setEmail(endereco.getEmail());
				enderecoEntidade.setLogradouro(endereco.getLogradouro());
				enderecoEntidade.setNumero(endereco.getNumero());
				enderecoEntidade.setSacado(sacadoEntidade);
				enderecoEntidade.setUf(endereco.getUf());
				em.persist(enderecoEntidade); // persiste
				
				enderecosEntidade.add(enderecoEntidade); // seta na colecao
			}
			
			sacadoEntidade.setEnderecos(enderecosEntidade);
			em.persist(sacadoEntidade); // persiste o sacado.
		} else {
			throw new CobrancaExcecao("Não foi informado o sacado para este boleto: " + boleto.getNumeroDocumento());
		}
		
		boletoEntidade.setSacado(sacadoEntidade); // seta o sacado no boleto.
		
		this.atualizaDadosBoleto(boleto, boletoEntidade);

		return boletoEntidade;
	}

	/**
	 * Gera o nosso numero e atribui a entidade Boleto
	 * @param boletoEntidade
	 * @param cedenteEntidade
	 */
	private void gerarNossoNumero(BoletoEntidade boletoEntidade, CedenteEntidade cedenteEntidade, boolean guiaArrecadacao) {
		Banco banco = Banco.findByCodigo(cedenteEntidade.getContaBancaria().getBanco().getCodigoBanco().intValue());
		long sequencial = obterSequencialNossoNumero(banco);
		BancoNegocio bancoNegocio = BancoNegocio.getInstance(banco);
		
		if (bancoNegocio != null) {
			// 2011-05-18: Decidimos utilizar o campo "codigo_geral" para armazenar o range ou o codigo de
			// prefixo para a geracao do nosso numero.
			//long codigo = cedenteEntidade.getId().getCodigoConvenio();
			long codigo = new Long(cedenteEntidade.getCodigoGeral().trim());
			int tamanho = 11;
			
			if (guiaArrecadacao) tamanho = 14;
			String nossoNumero = bancoNegocio.formatarNossoNumero(codigo, sequencial, tamanho);
	
			boletoEntidade.setNossoNumero(nossoNumero);
			boletoEntidade.setCedente(cedenteEntidade);
			
			if (boletoEntidade.getNumeroDocumento() == null)
				boletoEntidade.setNumeroDocumento(String.valueOf(sequencial));
			
			logger.info("Nosso numero gerado: " + nossoNumero);
		}
	}
	
	/**
	 * Recupera um boleto pelo seu ID
	 */
	@Override
	public BoletoEntidade recuperarBoleto(BoletoEntidade boletoEntidade) {
		boletoEntidade = em.find(BoletoEntidade.class, boletoEntidade.getIdBoleto());
		Date dataAcesso = new Date();
		boletoEntidade.setDataUltimoAcesso(dataAcesso);
		Long qtdAcessos = boletoEntidade.getQuantidadeAcessoEmail();
		long qtd = 1;
		if (qtdAcessos!=null){
			qtd = qtdAcessos.longValue() + 1;
		}
		boletoEntidade.setQuantidadeAcessoEmail(qtd);
		em.persist(boletoEntidade);
		return boletoEntidade;
	}

	/**
	 * Recupera um boleto pelo nosso numero
	 */
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public BoletoEntidade recuperarBoletoPeloNossoNumero(String nossoNumero, boolean atualizarContador) {
		Query query = em.createNamedQuery("boleto.retornarBoletoPorNossoNumero");
		query.setParameter("nossoNumero", nossoNumero);
		
		BoletoEntidade boletoEntidade = (BoletoEntidade) query.getSingleResult();
		
		if (atualizarContador) {
			Date dataAcesso = new Date();
			boletoEntidade.setDataUltimoAcesso(dataAcesso);
			Long qtdAcessos = boletoEntidade.getQuantidadeAcessoEmail();
			long qtd = 1;
			if (qtdAcessos!=null){
				qtd = qtdAcessos.longValue() + 1;
			}
			boletoEntidade.setQuantidadeAcessoEmail(qtd);
			em.persist(boletoEntidade);
		}
		return boletoEntidade;
	}

	/**
	 * Recupera um boleto pelo seu token.
	 */
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public BoletoEntidade recuperarBoletoPeloToken(String token) {
		Query query = em.createNamedQuery("retornarBoletoPorToken");
		query.setParameter("token", token);
		BoletoEntidade boletoEntidade = (BoletoEntidade) query.getSingleResult();
		Date dataAcesso = new Date();
		boletoEntidade.setDataUltimoAcesso(dataAcesso);
		Long qtdAcessos = boletoEntidade.getQuantidadeAcessoEmail();
		long qtd = 1;
		if (qtdAcessos!=null){
			qtd = qtdAcessos.longValue() + 1;
		}
		boletoEntidade.setQuantidadeAcessoEmail(qtd);
		em.persist(boletoEntidade);	
		return boletoEntidade;
	}
	
	/**
	 * Obtem o proximo sequencial de nosso número para o banco informado.
	 * @param banco
	 * @return
	 */
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public long obterSequencialNossoNumero(Banco banco) {
		String nomeSequencePostgres = String.format("cobranca.sq_nosso_numero_%03d", banco.getCodigo());
		
		Query query = em.createNativeQuery(String.format("SELECT nextval('%s')", nomeSequencePostgres));
		BigInteger value = (BigInteger) query.getSingleResult();

		long sequencia = value.longValue();
		logger.info("Retornado sequencia para banco [" + banco.getNome() + "]: " + sequencia);
		
		return sequencia;
	}

	/**
	 * Recupera um enderedo de sacado pelo CEP
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public EnderecoEntidade retornarEnderecoSacadoPorCep(String cpfCnpj, String cep) {
		EnderecoEntidade enderecoEntidade = null;

		Query query = em.createNamedQuery("retornarEnderecoSacadoPorCep");
		query.setParameter("cpfCnpj", cpfCnpj);
		query.setParameter("cep", cep);
		
		try {
			ArrayList<EnderecoEntidade> listaResultado = (ArrayList<EnderecoEntidade>) query.getResultList();
			if (listaResultado.size() > 0)
				enderecoEntidade = listaResultado.get(0);
		}
		catch (NoResultException nre) {  
			nre.printStackTrace(); 
		} 
		return enderecoEntidade;
	}

	/**
	 * Recupera um enderedo de sacado pelo Email
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public EnderecoEntidade retornarEnderecoSacadoPorEmail(String cpfCnpj, String email) {
		EnderecoEntidade enderecoEntidade = null;

		Query query = em.createNamedQuery("retornarEnderecoSacadoPorEmail");
		query.setParameter("cpfCnpj", cpfCnpj);
		query.setParameter("email", email);
		
		try {
			ArrayList<EnderecoEntidade> listaResultado = (ArrayList<EnderecoEntidade>) query.getResultList();
			if (listaResultado.size() > 0)
				enderecoEntidade = listaResultado.get(0);
		}
		catch (NoResultException nre) {  
			nre.printStackTrace(); 
		} 
		return enderecoEntidade;
	}

	/**
	 * Recupera um enderedo de cedente pelo CEP
	 */
	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public EnderecoEntidade retornarEnderecoCedentePorCep(String cpfCnpj, String cep) {
		Query query = em.createNamedQuery("retornarEnderecoCedentePorCep");
		//query.setParameter("cpfCnpj", cpfCnpj);
		query.setParameter("cep", cep);
		
		EnderecoEntidade enderecoEntidade = (EnderecoEntidade) query.getSingleResult();
		return enderecoEntidade;
	}
	
	/**
	 * Grava o array de bytes de PDF no disco
	 * @param byteArray
	 * @param codigoConvenio
	 * @param nossoNumero
	 * @return
	 */
	private File gravaBoletoEmDisco(byte[] byteArray, String codigoConvenio, String nossoNumero, String nossoNumero2) {
		File file = null;
		
		String nomeArquivoBoleto = retornaNomeArquivoBoletoPdf(codigoConvenio, nossoNumero, nossoNumero2); 
		
		ArquivoUtil.asseguraDiretorioExiste(nomeArquivoBoleto);
		
		try {
			logger.info("Gravando arquivo fisico de PDF: " + nomeArquivoBoleto);
			file = ArquivoUtil.bytes2File(nomeArquivoBoleto, byteArray);
			
		} catch (IOException e) {
			logger.error("Erro durante geração do PDF. " + e.getLocalizedMessage(), e);
			//throw new CobrancaExcecao("Erro durante geração do PDF. Causado por " + e.getLocalizedMessage(), e);
		}
		return file;
	}
	
	private String retornaNomeArquivoBoletoPdf(String codigoConvenio, String nossoNumero, String nossoNumero2) {
		String nomeArquivoBoleto = new String(properties.getProperty(DIRETORIO_ARQUIVOS_BOLETO_PROPERTIE));
		if (nossoNumero2 == null)
			nomeArquivoBoleto += String.format("/%s/boleto_%s.pdf", codigoConvenio, nossoNumero);
		else
			nomeArquivoBoleto += String.format("/%s/boleto_%s_%s.pdf", codigoConvenio, nossoNumero, nossoNumero2);
		return nomeArquivoBoleto;
	}
	
	/**
	 * Carrega o boleto fisico do disco. O Boleto é armazenado no array de bytes se existir.
	 * @param byteArray
	 * @param codigoConvenio
	 * @param nossoNumero
	 * @return
	 */
	private byte[] carregaBoletoDoDisco(CedenteEntidade cedenteEntidade, BoletoEntidade boletoEntidade, BoletoEntidade boletoEntidade2) {
		File file = null;
		
		byte[] byteArray = null;

		String codigoConvenio = String.valueOf(cedenteEntidade.getId().getCodigoConvenio());
		String nossoNumero = boletoEntidade.getNossoNumero();
		String nomeArquivoBoleto = retornaNomeArquivoBoletoPdf(codigoConvenio, nossoNumero, null);
		String nossoNumero2 = null;
		
		if (boletoEntidade2 != null) {
			nossoNumero2 = boletoEntidade2.getNossoNumero();
		}
		
		if (!ArquivoUtil.isFileExists(nomeArquivoBoleto) && nossoNumero2 != null) {
			nomeArquivoBoleto = retornaNomeArquivoBoletoPdf(codigoConvenio, nossoNumero, nossoNumero2);
		}
		
		if (!ArquivoUtil.isFileExists(nomeArquivoBoleto)) {
			// Nao encontrou boleto no sistema de arquivos. Entao tenta gerar o boleto fisico.
			logger.info("Arquivo fisico de PDF nao existe. Tentando gerar o boleto...");
			byteArray = this.gerarBoletoGuiaFisico(boletoEntidade, boletoEntidade2, cedenteEntidade);
			
			file = gravaBoletoEmDisco(byteArray, codigoConvenio, nossoNumero, nossoNumero2);
			
			if (file == null) {
				logger.error("Nao foi possivel gravar o arquivo PDF em disco.");
			}
		}
		else {
			try {
				byteArray = null;
				file = new File(nomeArquivoBoleto);
				
				if (file != null) {
					byteArray = ArquivoUtil.loadBytesFromStream(new FileInputStream(file));
				}
				
			} catch (IOException e) {
				logger.error("Erro lendo arquivo PDF do disco. " + e.getLocalizedMessage(), e);
			}
		}
		return byteArray;
	}
	
	/**
	 * Gera um boleto avulso sem cobranca registrada com os dados informados.
	 * @param cedente
	 * @param sacado
	 * @param dataVencimento
	 * @param valorBoleto
	 * @param numeroDocumento
	 */
	public BoletoDTO gerarBoletoAvulso(CedenteDTO cedente, SacadoDTO sacado, 
			Date dataVencimento, Double valorBoleto, String numeroDocumento, 
			String assuntoEmail, String textoEmail,byte[] anexoRelatorio, byte[] anexoPlanilha, 
			boolean guiaArrecadacao) {
		
		CedenteEntidade cedenteEntidade = retornarCedenteEntidade(cedente);
		CobrancaEntidade cobrancaEntidade = new CobrancaEntidade();
		
		BoletoDTO boletoDTO = new BoletoDTO();
		boletoDTO.setDataVencimento(dataVencimento);
		boletoDTO.setValorBoleto(valorBoleto);
		boletoDTO.setSacado(sacado);
		boletoDTO.setCedente(cedente);
		boletoDTO.setNumeroDocumento(numeroDocumento);
		boletoDTO.setDataEmissao(new Date());
		boletoDTO.setAnexoPlanilhaEmail(anexoPlanilha);
		boletoDTO.setAnexoRelatorioEmail(anexoRelatorio);
		boletoDTO.setTextoEmail(textoEmail);
		boletoDTO.setAssuntoEmail(assuntoEmail);
		
		// Retorna a entidade de boleto atualizando o Sacado e Enderecos preenchidos no BoletoDTO
		BoletoEntidade boletoEntidade = retornarNovoBoletoEntidade(boletoDTO, cedenteEntidade, cobrancaEntidade);
		gerarNossoNumero(boletoEntidade, cedenteEntidade, guiaArrecadacao);
		
		if (numeroDocumento == null) {
			boletoEntidade.setAssuntoEmail(assuntoEmail + " - " + boletoEntidade.getNumeroDocumento());
		}
		
		byte[] byteArray = null;
		
		if (!guiaArrecadacao) {
			Boleto boleto = DominioUtil.retornarDominioBoleto(boletoEntidade);
			
			GeradorBoleto geradorBoleto = new GeradorBoleto(boleto, new File(TEMPLATE_BOLETO.getFile()));
			byteArray = geradorBoleto.getPdfAsByteArray();
			boletoEntidade.setDataEmissao(boleto.getDataEmissao());

			EspecieTituloEntidade especieTituloEntidade = em.find(EspecieTituloEntidade.class, new Long(boleto.getEspecieTitulo().getValor()));
			boletoEntidade.setEspecieTitulo(especieTituloEntidade);
		}
		else {
			Guia guia = DominioUtil.retornarDominioGuia(boletoEntidade, null);
			guia.setInstrucoes(cedente.getInstrucoes());
			
			GeradorGuia geradorGuia = new GeradorGuia(guia, TEMPLATE_GUIA);
			byteArray = geradorGuia.getPdfAsByteArray();
		}
		
		// Alteracao para gerar arquivo em disco
		File file = gravaBoletoEmDisco(byteArray, String.valueOf(cedente.getCodigoConvenio()), boletoEntidade.getNossoNumero(), null);
		
		if (file == null) {
			// Se nao consegui gravar no disco gravo no campo BLOB
			boletoEntidade.setBoletoFisico(byteArray);
		}
		
		boletoEntidade.setCobranca(cobrancaEntidade);

		if (textoEmail != null) {
			String tokenAcessoEmail = TextoUtil.gerarTextoUUID();
			boletoEntidade.setTokenAcessoEmail(tokenAcessoEmail);
			logger.info("Token de download gerado: " + tokenAcessoEmail);
		}

		List<BoletoEntidade> listaBoletosEntidade = new ArrayList<BoletoEntidade>();
		listaBoletosEntidade.add(boletoEntidade); // adiciona boleto a lista que sera pesistida.
		cobrancaEntidade.setBoletos(listaBoletosEntidade);
		
		em.persist(boletoEntidade);
		em.persist(cobrancaEntidade);
		
		em.flush();
		
		boletoDTO.setIdBoleto(boletoEntidade.getIdBoleto());
		boletoDTO.setNossoNumero(boletoEntidade.getNossoNumero());
		boletoDTO.setNumeroDocumento(boletoEntidade.getNumeroDocumento());
		boletoDTO.setAssuntoEmail(boletoEntidade.getAssuntoEmail());
		
		// Envia o mail para o administrativo
		if (textoEmail != null) {
			enviarEmail(boletoEntidade);
		}		
		return boletoDTO;
	}
	
	/**
	 * Retorna a lista de bancos existentes.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BancoDTO> retornarListaBancos() {
		List<BancoDTO> listaBancos = new ArrayList<BancoDTO>();

		try {
			Query query = em.createNamedQuery("retornarListaBancos");
			List<BancoEntidade> resultList = query.getResultList();
			
			for (BancoEntidade bancoEntidade : resultList) {
				BancoDTO bancoDTO = new BancoDTO();
				bancoDTO.setCodigoBanco(String.format("%03d", bancoEntidade.getCodigoBanco()));
				bancoDTO.setNomeBanco(bancoEntidade.getNomeBanco());
				
				listaBancos.add(bancoDTO);
			}
			
		} catch (javax.persistence.NoResultException nre) {
			logger.info("Não existe bancos cadastrados.");			
		}
	
		return listaBancos;
	}

	/**
	 * Gera um boleto individual.
	 * Boleto individual nao envia email.
	 * @param cedente
	 * @param sacado
	 * @param valorBoleto
	 * @return
	 */
	public BoletoDTO gerarBoletoIndividual(CedenteDTO cedente, SacadoDTO sacado, Double valorBoleto, String numeroDocumento) {
		
		BoletoDTO boletoDTO = this.gerarBoletoAvulso(cedente, sacado, null, valorBoleto, numeroDocumento, null, null, null, null, true);
		
		return boletoDTO;
	}

	/**
	 * Gera um ou dois boletos de arrecadacao (Guia). 
	 * Se informado mais de um boleto devera ser informado tambem os impostos.
	 * @param boletos
	 * @param impostos
	 * @return
	 */
	public List<BoletoDTO> gerarBoletoArrecadacao(List<BoletoDTO> boletos) {
		CobrancaEntidade cobrancaEntidade = null;	//new CobrancaEntidade();
		List<BoletoEntidade> listaBoletosEntidade = new ArrayList<BoletoEntidade>();
		
		BoletoEntidade boletoEntidade = null;
		BoletoEntidade boletoEntidade2 = null;
		
		BoletoDTO boletoDTO = boletos.get(0);
		BoletoDTO boletoDTO2 = null;
		
		CedenteDTO cedente = boletoDTO.getCedente();
		CedenteEntidade cedenteEntidade = retornarCedenteEntidade(cedente);
		
		if (cedenteEntidade.getLogo() == null) {
			cedenteEntidade.setLogo(boletoDTO.getImagemLogo());
		}
		
		cedenteEntidade.setInstrucaoSacado(boletoDTO.getCedente().getInstrucoes()); 
		em.persist(cedenteEntidade);

		// Retorna a entidade de boleto atualizando o Sacado e Enderecos preenchidos no BoletoDTO
		boletoEntidade = retornarNovoBoletoEntidade(boletoDTO, cedenteEntidade, cobrancaEntidade);
		gerarNossoNumero(boletoEntidade, cedenteEntidade, true);
	
		boletoEntidade.setCobranca(cobrancaEntidade);
		listaBoletosEntidade.add(boletoEntidade); // adiciona boleto a lista que sera pesistida.
		
		if (boletoDTO.getValorIR() != null) {
			boletoEntidade.setValorIr(new BigDecimal(boletoDTO.getValorIR()));
		}
		if (boletoDTO.getValorPIS() != null) {
			boletoEntidade.setValorPis(new BigDecimal(boletoDTO.getValorPIS()));
		}
		if (boletoDTO.getValorCOFINS() != null) { 
			boletoEntidade.setValorCofins(new BigDecimal(boletoDTO.getValorCOFINS()));
		}
		if (boletoDTO.getValorCSLL() != null) {
			boletoEntidade.setValorCsll(new BigDecimal(boletoDTO.getValorCSLL()));
		}
		if (boletoDTO.getValorISS() != null) {
			boletoEntidade.setValorIss(new BigDecimal(boletoDTO.getValorISS()));
		}
		
		boletoEntidade.setInstrucoesBancarias(boletoDTO.getInstrucoesBancarias());
		em.persist(boletoEntidade);

		//em.persist(cobrancaEntidade);

		if (boletos.size() > 1) {
			boletoDTO2 = boletos.get(1);
			CedenteDTO cedente2 = boletoDTO2.getCedente();
			CedenteEntidade cedenteEntidade2 = retornarCedenteEntidade(cedente2);

			if (cedenteEntidade2.getLogo() == null) {
				cedenteEntidade2.setLogo(boletoDTO2.getImagemLogo());
				em.persist(cedenteEntidade2);
			}

			// Retorna a entidade de boleto atualizando o Sacado e Enderecos preenchidos no BoletoDTO
			boletoEntidade2 = retornarNovoBoletoEntidade(boletoDTO2, cedenteEntidade2, cobrancaEntidade);
			gerarNossoNumero(boletoEntidade2, cedenteEntidade2, true);
		
			boletoEntidade2.setCobranca(cobrancaEntidade);
			listaBoletosEntidade.add(boletoEntidade2); // adiciona boleto a lista que sera pesistida.
			em.persist(boletoEntidade2);
		}

		em.flush();

		byte[] byteArray = this.gerarBoletoGuiaFisico(boletoEntidade, boletoEntidade2, cedenteEntidade);
		
		//cobrancaEntidade.setBoletos(listaBoletosEntidade);
		
		// Atualiza nosso numero para retorno
		Long codigoConvenio = boletoEntidade.getCedente().getId().getCodigoConvenio(); 
			
		// Alteracao para gerar arquivo em disco
		File file = gravaBoletoEmDisco(byteArray, String.valueOf(codigoConvenio), boletoEntidade.getNossoNumero(), boletoEntidade2 != null ? boletoEntidade2.getNossoNumero() : null);
		
		if (file == null) {
			// Se nao consegui gravar no disco gravo no campo BLOB
			//boletoEntidade.setBoletoFisico(byteArray);
			//em.persist(boletoEntidade);
		}
		
		// Atualiza dados do DTO para retorno
		boletoDTO.setCaminhoFisicoBoleto(file.getAbsolutePath());
		boletoDTO.setIdBoleto(boletoEntidade.getIdBoleto());
		boletoDTO.setNossoNumero(boletoEntidade.getNossoNumero());
		
		if (boletoDTO2 != null) {
			boletoDTO2.setIdBoleto(boletoEntidade2.getIdBoleto());
			boletoDTO2.setNossoNumero(boletoEntidade2.getNossoNumero());
		}
		
		em.flush();

		return boletos;
	}
	
	/**
	 * Gera novamente o boleto fisico pelos dados ja cadastrados nos boletos do banco do 
	 * sistema de cobranca.
	 * @param nossoNumero
	 * @param nossoNumero2
	 * @param instrucoes
	 * @return
	 */
	public byte[] regerarBoletoArrecadacao(String nossoNumero, String nossoNumero2, String instrucoes) {
		byte[] byteArray = null;

		BoletoEntidade boletoEntidade = recuperarBoletoPeloNossoNumero(nossoNumero, true);
		BoletoEntidade boletoEntidade2 = null;
		
		if (nossoNumero2 != null) {
			boletoEntidade2 = recuperarBoletoPeloNossoNumero(nossoNumero2, true);
		}
		
		logger.info("Regerando boleto fisico para nosso numero: " + nossoNumero + " " + (nossoNumero2 != null ? nossoNumero2 : ""));
		
		if (boletoEntidade != null) {
			if (instrucoes != null) {
				// Atualiza instrucoes bancarias informada.
				boletoEntidade.setInstrucoesBancarias(instrucoes);
				em.persist(boletoEntidade);
				em.flush();
			}
			
			CedenteEntidade cedenteEntidade = boletoEntidade.getCedente();
			byteArray = carregaBoletoDoDisco(cedenteEntidade, boletoEntidade, boletoEntidade2);
		}
		
		return byteArray;
	}

	/**
	 * Gera o boleto fisico PDF no disco e retorna o array de bytes.
	 * @param boletoEntidade
	 * @param guiaArrecadacao
	 * @return
	 */
	private byte[] gerarBoletoGuiaFisico(BoletoEntidade boletoEntidade, BoletoEntidade boletoEntidade2, 
			CedenteEntidade cedenteEntidade) {
		
		byte[] byteArray = null;
		BufferedImage image = null;

		logger.info("Gerando array de bytes de PDF de boleto...");
		
		// Seta logo
		if (cedenteEntidade.getLogo() != null) {
			byte[] arrayLogo = cedenteEntidade.getLogo();
			InputStream in = new ByteArrayInputStream(arrayLogo);
			try {
				image = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Guia guia = DominioUtil.retornarDominioGuia(boletoEntidade, boletoEntidade2);
		
		if (boletoEntidade.getInstrucoesBancarias() == null) {
			guia.setInstrucoes(cedenteEntidade.getInstrucaoSacado());
		}
		else {
			guia.setInstrucoes(boletoEntidade.getInstrucoesBancarias());
		}
		
		guia.setDataEmissao(boletoEntidade.getDataEmissao());
		guia.getArrecadacao().setDataDoDocumento(boletoEntidade.getDataEmissao());
		
		if (image != null) {
			guia.addImagensExtras("txtLogoEmpresa", image);
		}
		
		if (boletoEntidade.getValorIr() != null) {
			guia.addTextosExtras("txtValorIR", TextoUtil.formataValor(boletoEntidade.getValorIr().doubleValue()));
		}
		if (boletoEntidade.getValorPis()!= null) {
			guia.addTextosExtras("txtValorPIS", TextoUtil.formataValor(boletoEntidade.getValorPis().doubleValue()));
		}
		if (boletoEntidade.getValorCofins() != null) { 
			guia.addTextosExtras("txtValorCOFINS", TextoUtil.formataValor(boletoEntidade.getValorCofins().doubleValue()));
		}
		if (boletoEntidade.getValorCsll() != null) {
			guia.addTextosExtras("txtValorCSLL", TextoUtil.formataValor(boletoEntidade.getValorCsll().doubleValue()));
		}
		if (boletoEntidade.getValorIss() != null) {
			guia.addTextosExtras("txtValorISS", TextoUtil.formataValor(boletoEntidade.getValorIss().doubleValue()));
		}

		GeradorGuia geradorGuia = null;
		
		if (boletoEntidade.getCedente().getCodigoGeral().equals(CONVENIO_AL)){
			geradorGuia = new GeradorGuia(guia, TEMPLATE_GUIA_SIMPLES_IMPOSTOS);
		}
		else if(boletoEntidade2 == null) {
			geradorGuia = new GeradorGuia(guia, TEMPLATE_GUIA_SIMPLES);
		}
		else {
			geradorGuia = new GeradorGuia(guia, TEMPLATE_GUIA_DUPLA);
		}
		
		byteArray = geradorGuia.getPdfAsByteArray();
		
		logger.info("PDF de boleto gerado. " + byteArray.length + " bytes.");
		
		return byteArray;
	}

	/**
	 * Retorna o array de bytes do boleto fisico
	 * @param nossoNumero
	 * @return
	 */
	public byte[] retornarBoletoFisico(String nossoNumero, String nossoNumero2) {
		BoletoEntidade boletoEntidade = recuperarBoletoPeloNossoNumero(nossoNumero, true);
		BoletoEntidade boletoEntidade2 = null;
		
		if (nossoNumero2 != null) {
			boletoEntidade2 = recuperarBoletoPeloNossoNumero(nossoNumero2, true);
		}
		
		logger.info("Retornando boleto fisico para nosso numero: " + nossoNumero + " " + (nossoNumero2 != null ? nossoNumero2 : ""));
		
		if (boletoEntidade != null) {
			byte[] byteArray = boletoEntidade.getBoletoFisico();
			
			if (byteArray == null) {
				// Tenta retornar do disco
				CedenteEntidade cedenteEntidade = boletoEntidade.getCedente();
				byteArray = carregaBoletoDoDisco(cedenteEntidade, boletoEntidade, boletoEntidade2);
			}
			return byteArray;
		}
		
		return null;
	}
	
	/**
	 * Retorna a lista de boleto de acordo com os parametros da pesquisa.
	 * @param pesquisaBoleto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BoletoDTO> retornarListaBoletos(PesquisaBoletoDTO pesquisaBoleto) {
		List<BoletoDTO> lista = new ArrayList<BoletoDTO>();
		
		String selectQuery = "SELECT boleto FROM BoletoEntidade boleto";
		String whereClause = "";
		ArrayList<String> whereClauses = new ArrayList<String>();
		
		if (pesquisaBoleto.getDataEmissaoInicial() != null) {
			whereClauses.add("boleto.dataEmissao >= :dataEmissaoInicial");
			if (pesquisaBoleto.getDataEmissaoFinal() == null)
				pesquisaBoleto.setDataEmissaoFinal(pesquisaBoleto.getDataEmissaoInicial());
			whereClauses.add("boleto.dataEmissao <= :dataEmissaoFinal");
		}
		
		if (pesquisaBoleto.getDataPagamentoInicial() != null) {
			whereClauses.add("boleto.dataPagamento >= :dataPagamentoInicial");
			if (pesquisaBoleto.getDataPagamentoFinal() == null)
				pesquisaBoleto.setDataPagamentoFinal(pesquisaBoleto.getDataPagamentoInicial());
			whereClauses.add("boleto.dataPagamento <= :dataPagamentoFinal");
		}

		if (pesquisaBoleto.getDataCreditoInicial() != null) {
			whereClauses.add("boleto.dataCredito >= :dataCreditoInicial");
			if (pesquisaBoleto.getDataCreditoFinal() == null)
				pesquisaBoleto.setDataCreditoFinal(pesquisaBoleto.getDataCreditoInicial());
			whereClauses.add("boleto.dataCredito <= :dataCreditoFinal");
		}
		
		if (pesquisaBoleto.getConvenio() != null) {
			whereClauses.add("boleto.cedente.id.codigoConvenio <= :convenio");
		}

		if (pesquisaBoleto.getNossoNumero() != null) {
			whereClauses.add("boleto.nossoNumero <= :nossoNumero");
		}

		if (pesquisaBoleto.getNumeroDocumento() != null) {
			whereClauses.add("boleto.numeroDocumento <= :numeroDocumento");
		}
		
		for (String clause : whereClauses) {
			if (whereClause.length() > 0) whereClause += " AND ";
			whereClause += clause;
		}
		
		if (whereClause.length() > 0) whereClause = " WHERE " + whereClause;

		if (whereClause.length() > 0) {
			selectQuery += whereClause;
			
			Query query = em.createQuery(selectQuery);
			
			if (selectQuery.indexOf("dataEmissaoInicial") > 0)
				query.setParameter("dataEmissaoInicial", pesquisaBoleto.getDataEmissaoInicial());
			
			if (selectQuery.indexOf("dataEmissaoFinal") > 0)
				query.setParameter("dataEmissaoFinal", pesquisaBoleto.getDataEmissaoFinal());
			
			if (selectQuery.indexOf("dataPagamentoInicial") > 0)
				query.setParameter("dataPagamentoInicial", pesquisaBoleto.getDataPagamentoInicial());
			
			if (selectQuery.indexOf("dataPagamentoFinal") > 0)
				query.setParameter("dataPagamentoFinal", pesquisaBoleto.getDataPagamentoFinal());
			
			if (selectQuery.indexOf("dataCreditoInicial") > 0)
				query.setParameter("dataCreditoInicial", pesquisaBoleto.getDataCreditoInicial());
			
			if (selectQuery.indexOf("dataCreditoFinal") > 0)
				query.setParameter("dataCreditoFinal", pesquisaBoleto.getDataCreditoFinal());
			
			if (selectQuery.indexOf("convenio") > 0)
				query.setParameter("convenio", pesquisaBoleto.getConvenio());
			
			if (selectQuery.indexOf("nossoNumero") > 0)
				query.setParameter("nossoNumero", pesquisaBoleto.getNossoNumero());
			
			if (selectQuery.indexOf("numeroDocumento") > 0)
				query.setParameter("numeroDocumento", pesquisaBoleto.getNumeroDocumento());
			
			try {
				List<BoletoEntidade> listaBoletosEntidade = query.getResultList();
				
				for (BoletoEntidade boletoEntidade : listaBoletosEntidade) {
					BoletoDTO boletoDTO = new BoletoDTO();
					
					boletoDTO.setIdBoleto(boletoEntidade.getIdBoleto());
					boletoDTO.setNossoNumero(boletoEntidade.getNossoNumero());
					boletoDTO.setDataEmissao(boletoEntidade.getDataEmissao());
					boletoDTO.setDataVencimento(boletoEntidade.getDataVencimento());
					boletoDTO.setDataPagamento(boletoEntidade.getDataPagamento());
					boletoDTO.setDataCredito(boletoEntidade.getDataCredito());
					boletoDTO.setNumeroDocumento(boletoEntidade.getNumeroDocumento());
					boletoDTO.setAssuntoEmail(boletoEntidade.getAssuntoEmail());
					boletoDTO.setTextoEmail(boletoEntidade.getTextoEmail());
					boletoDTO.setValorAbatimento(boletoEntidade.getValorAbatimento().doubleValue());
					boletoDTO.setValorBoleto(boletoEntidade.getValorBoleto().doubleValue());
					boletoDTO.setValorCredito(boletoEntidade.getValorCredito());
					boletoDTO.setValorDesconto(boletoEntidade.getValorDesconto().doubleValue());
					boletoDTO.setValorIof(boletoEntidade.getValorIof().doubleValue());
					boletoDTO.setValorJurosMora(boletoEntidade.getValorJurosMora().doubleValue());
					boletoDTO.setValorMulta(boletoEntidade.getValorMulta().doubleValue());
					
					CedenteDTO cedenteDTO = new CedenteDTO();
					cedenteDTO.setCodigoConvenio(boletoEntidade.getCedente().getId().getCodigoConvenio());
					cedenteDTO.setCpfCnpjCedente(boletoEntidade.getCedente().getId().getNumeroCpfCnpjCedente());
					boletoDTO.setCedente(cedenteDTO);
					
					SacadoDTO sacadoDTO = new SacadoDTO();
					sacadoDTO.setCpfCnpjSacado(boletoEntidade.getSacado().getNumeroCpfCnpjSacado());
					sacadoDTO.setNome(boletoEntidade.getSacado().getNome());
					boletoDTO.setSacado(sacadoDTO);
					
					SacadorAvalistaDTO sacadorAvalistaDTO = new SacadorAvalistaDTO();
					if (boletoEntidade.getSacadorAvalista() != null) {
						sacadorAvalistaDTO.setCpfCnpjSacadorAvalista(boletoEntidade.getSacadorAvalista().getNumeroCpfCnpjAvalista());
						sacadorAvalistaDTO.setNome(boletoEntidade.getSacadorAvalista().getNome());
					}
					boletoDTO.setSacadorAvalista(sacadorAvalistaDTO);
					
					lista.add(boletoDTO);
				}
			}
			catch (NoResultException nre) {  
				nre.printStackTrace(); 
			} 
		}		
		return lista;
	}

	/**
	 * Envia o email com boleto para o nosso numero informado
	 * @param nossoNumero
	 * @return
	 */
	public boolean enviarEmailCobranca(BoletoDTO boletoDTO) {
		String nossoNumero = boletoDTO.getNossoNumero();
		BoletoEntidade boletoEntidade = recuperarBoletoPeloNossoNumero(nossoNumero, false);
		
		if (boletoEntidade != null) {
			return enviarEmail(boletoEntidade);
		}
		
		return false;
	}

	/**
	 * Retorna verdadeiro se existir boleto de cobranca para o documento informado.
	 * @param numeroDocumento
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isExisteBoletoCobranca(String numeroDocumento) {
		List<BoletoEntidadeLite> listaBoletos = null;
		
		Query query = em.createNamedQuery("retornarBoletoLitePorNumeroDocumento");
		query.setParameter("numeroDocumento", numeroDocumento);

		try {
			listaBoletos = (ArrayList<BoletoEntidadeLite>) query.getResultList();
			return (listaBoletos != null && listaBoletos.size() > 0);
		}
		catch (NoResultException nre) {
			logger.info("Nao existe na base boleto com numero de documento: " + numeroDocumento);
		}
		return false;
	}

}
