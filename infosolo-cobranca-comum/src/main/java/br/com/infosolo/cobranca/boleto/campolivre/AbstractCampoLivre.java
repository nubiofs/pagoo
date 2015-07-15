package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.Convenio;
import br.com.infosolo.cobranca.boleto.TipoSeguimento;
import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.util.AbstractLinhaDeCampos;
import br.com.infosolo.cobranca.util.BancoUtil;
import br.com.infosolo.comum.util.Logger;

/**
 * <p>
 * Esta classe é responsável por determinar a interface campo livre e também
 * determinar qual implementação de campo livre se aplica a um determinada
 * arrecadação.
 * </p>
 * 
 * <p>
 * Uma outra forma de analisar esta classe é sob o prisma de uma Abstract
 * Factory.
 * </p>
 * 
 * <p>
 * <dl>
 * <dt><strong>Field Livre:</strong>
 * <dd>É um espaço reservado no código de barras e a sua implementação varia de
 * banco para banco.</dd>
 * </dt>
 * </dl>
 * </p>
 * 
 * @author Misael Barreto
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
abstract class AbstractCampoLivre extends AbstractLinhaDeCampos implements CampoLivre {
	private static final long serialVersionUID = 4605730904122445595L;
	
	private static Logger logger = new Logger(AbstractCampoLivre.class);

	protected AbstractCampoLivre(Integer fieldsLength, TipoSeguimento tipoSeguimento) {
		super();
		
		Integer STRING_LENGTH;
		STRING_LENGTH = CampoLivreUtil.getTamanhoCorreto(tipoSeguimento);	
		
		setStringLength(STRING_LENGTH);
		setFieldsLength(fieldsLength);
	}

	protected AbstractCampoLivre(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	/**
	 * 
	 * @param arrecadacao
	 * @return
	 * 
	 * @throws NaoSuportadoBancoExecao Caso o banco informado no convênio não 
	 * tenha nenhuma implementação de campo livre.
	 * 
	 * @throws NaoSuportadoCampoLivreExcecao Caso exista implementações de campo 
	 * livre para o banco informa no convênio, mas nenhuma dessas implementações 
	 * foram adequadas para os dados da arrecadação.
	 * 
	 * @throws CampoLivreExecao Caso ocorra algum problema na geração do campo livre.
	 */
	static CampoLivre create(Arrecadacao arrecadacao) {
		CampoLivre campoLivre = null;
		Convenio convenio = null;
		
		try{		
			convenio = arrecadacao.getConvenio();

			if (isConvenioOK(convenio)) {
				logger.debug("Campo Livre do Banco: " + convenio.getBanco().getNome());
	
				if (convenio.getBanco() == Banco.BANCO_BRASIL) {
					campoLivre = AbstractCLBancoDoBrasil.create(arrecadacao);
				}					
				else if (convenio.getBanco() == Banco.BANCO_CAIXA) {
					campoLivre = AbstractCLCaixaEconomica.create(arrecadacao);
						
				} else {
					/*
					 * Se chegar até este ponto, é sinal de que para o banco em
					 * questão, apesar de estar definido no EnumBancos, não há
					 * implementações de campo livre, logo considera-se o banco com
					 * não suportado.
					 */
					throw new NaoSuportadoBancoExecao();
				}
	
				/*
				 * Se chegar neste ponto e nenhum campo livre foi definido, então é
				 * sinal de que existe implementações de campo livre para o banco em
				 * questão, só que nenhuma destas implementações serviu e a classe
				 * abstrata responsável por fornecer o campo livre não gerou a
				 * exceção NotSupportedCampoLivreException. Trata-se de uma mensagem
				 * genérica que será utilizada somente em último caso.
				 */
				if (campoLivre == null) {
					throw new NaoSuportadoCampoLivreExcecao(
							"Não há implementações de campo livre para o banco "
									+ BancoUtil.retornarCodigoBancoBACEN(convenio.getBanco())
									+ " compatíveis com as "
									+ "caracteríticas da arrecadação informada.");
				}
			}
		
		} catch(Exception e) {
			if(e instanceof CampoLivreExecao)
				throw (CampoLivreExecao)e;
			else
				throw new CampoLivreExecao(e);		
		}

		return campoLivre;
	}
	
	private static boolean isConvenioOK(Convenio convenio) {
		return (  convenio != null && convenio.getBanco() != null );
	}
	
	/**
	 * 
	 * @param titulo
	 * @return
	 * 
	 * @throws NaoSuportadoBancoExecao Caso o banco informado na conta bancária não tenha nenhuma
	 * implementação de campo livre.
	 * @throws NaoSuportadoCampoLivreExcecao Caso exista implementações de campo livre para o banco informa
	 * na conta bancária, mas nenhuma dessas implementações foram adequadas para os dados do título.
	 * @throws CampoLivreExecao Caso ocorra algum problema na geração do campo livre.
	 */
	static CampoLivre create(Boleto boleto) {
		CampoLivre campoLivre = null;
		ContaBancaria contaBancaria = null;
		
		try {
			contaBancaria = boleto.getCedente().getContaBancaria();
			logger.debug("Campo Livre do Banco: " + contaBancaria.getBanco().getNome());
	
			/*
			 * A conta bancária passada não é sincronizada.
			 */
			if (isContaBacariaOK(contaBancaria)) {
				if (contaBancaria.getBanco() == Banco.BANCO_BRASIL) {
					campoLivre = AbstractCLBancoDoBrasil.create(boleto);
				}
				else if (contaBancaria.getBanco() == Banco.BANCO_HSBC) {
					campoLivre = AbstractCLHSBC.create(boleto);
				}
	
//					case BANCO_BRADESCO:
//						campoLivre = AbstractCLBradesco.create(titulo);
//						break;
//	
//					case BANCO_ABN_AMRO_REAL:
//						campoLivre = AbstractCLBancoReal.create(titulo);
//						break;
//	
//					case CAIXA_ECONOMICA_FEDERAL:
//						campoLivre = AbstractCLCaixaEconomicaFederal.create(titulo);
//						break;
//	
//					case UNIBANCO:
//						campoLivre = AbstractCLUnibanco.create(titulo);
//						break;
//	
//					case BANCO_ITAU:
//						campoLivre = AbstractCLItau.create(titulo);
//						break;
//	
//					case BANCO_SAFRA:
//						campoLivre = AbstractCLBancoSafra.create(titulo);
//						break;
//	
//					case BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL:
//						campoLivre = AbstractCLBanrisul.create(titulo);
//						break;
//						
//					case MERCANTIL_DO_BRASIL:
//						campoLivre = AbstractCLMercantilDoBrasil.create(titulo);
//						break;
//						
//					case NOSSA_CAIXA:
//						campoLivre = AbstractCLNossaCaixa.create(titulo);
//						break;
//					
//					case BANCO_DO_ESTADO_DO_ESPIRITO_SANTO:
//						campoLivre = AbstractCLBanestes.create(titulo);
//						break;
				else {	
					/*
					 * Se chegar até este ponto, é sinal de que para o banco em
					 * questão, apesar de estar definido no EnumBancos, não há
					 * implementações de campo livre, logo considera-se o banco com
					 * não suportado.
					 */
					throw new NaoSuportadoBancoExecao();
				}
	
				/*
				 * Se chegar neste ponto e nenhum campo livre foi definido, então é
				 * sinal de que existe implementações de campo livre para o banco em
				 * questão, só que nenhuma destas implementações serviu e a classe
				 * abstrata responsável por fornecer o campo livre não gerou a
				 * exceção NotSupportedCampoLivreException. Trata-se de uma mensagem
				 * genérica que será utilizada somente em último caso.
				 */
				if (campoLivre == null) {
					throw new NaoSuportadoCampoLivreExcecao(
							"Não há implementações de campo livre para o banco "
							+ BancoUtil.retornarCodigoBancoBACEN(contaBancaria.getBanco())
							+ " compatíveis com as "
							+ "caracteríticas do título informado.");
				}
				
			}
		
		} catch(Exception e) {
			if(e instanceof CampoLivreExecao)
				throw (CampoLivreExecao)e;
			else
				throw new CampoLivreExecao(e);		
		}

		return campoLivre;
	}

	/**
	 * <p>
	 * Verifica se a conta bancária passada está ok em relação aos atributos
	 * usados nessa na composição do campo livre.
	 * </p>
	 * 
	 * @param conta
	 * @return se ok.
	 * 
	 * @since 0.2
	 */
	private static boolean isContaBacariaOK(ContaBancaria conta) {
		return (conta != null && conta.getBanco() != null);
	}

}
