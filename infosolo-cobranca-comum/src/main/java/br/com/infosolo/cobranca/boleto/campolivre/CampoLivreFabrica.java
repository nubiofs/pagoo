package br.com.infosolo.cobranca.boleto.campolivre;

import org.apache.commons.lang3.StringUtils;

import br.com.infosolo.cobranca.boleto.TipoSeguimento;
import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.cobranca.util.Preenchedor;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;


/**
 * <p>
 * Esta classe tem como finalidade encapsular toda a lógica de criação de um
 * campo livre e de fornecer para o pacote
 * <code>br.com.infosolo.cobranca.boleto.campolivre.guia</code> 
 * um único ponto de acesso ao mesmo.
 * </p>
 * 
 * @author Misael Barreto 
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public final class CampoLivreFabrica {
	private static final long serialVersionUID = 8572635341880404937L;
	private static Logger logger = new Logger(CampoLivreFabrica.class);


	/**
	 * <p>
	 * Devolve um <code>CampoLivre</code> de acordo com o Banco contido no convênio.
	 * </p> 
	 * <p>
	 * Caso exista implementação para o banco o retorno terá uma referência não nula.
	 * </p>
	 * 
	 * @param arrecadacao
	 * 
	 * @return Uma referência para um CampoLivre.
	 * @throws NaoSuportadoBancoExecao 
	 * @throws NaoSuportadoCampoLivreExcecao 
	 */
	public static CampoLivre create(Arrecadacao arrecadacao) 
	throws NaoSuportadoBancoExecao, NaoSuportadoCampoLivreExcecao {

		return AbstractCampoLivre.create(arrecadacao);
	}
	
	/**
	 * Devolve um CampoLivre de acordo a partir de uma String.
	 * 
	 * @param strCampoLivre
	 * 
	 * @return Uma referência para um CampoLivre.
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public static CampoLivre create(String strCampoLivre, TipoSeguimento tipoSeguimento) {
		
		CampoLivre campoLivre = null;
		final Integer tamanhoCorreto = CampoLivreUtil.getTamanhoCorreto(tipoSeguimento); 		

		TextoUtil.checkNotNull(strCampoLivre);
		TextoUtil.checkNotBlank(strCampoLivre, "O Campo Livre não deve ser vazio!");
		
		strCampoLivre = StringUtils.strip(strCampoLivre); 
		
		if (CampoLivreUtil.tamanhoEstaCorreto(strCampoLivre, tipoSeguimento)) {
			if (CampoLivreUtil.naoExisteEspacoEmBranco(strCampoLivre, tipoSeguimento)) {
				if (StringUtils.isNumeric(strCampoLivre)) {

					campoLivre = new CampoLivre() {
						private static final long serialVersionUID = -7592488081807235080L;

						Campo<String> campo = new Campo<String>(StringUtils.EMPTY,
								tamanhoCorreto, Preenchedor.ZERO_ESQUERDA);

						public void read(String str) {
							campo.read(str);
						}
						
						public String write() {
							return campo.write();
						}
					};
					
					campoLivre.read(strCampoLivre);
					
				} else {					
					IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] deve ser uma String numérica!");
					logger.error(StringUtils.EMPTY, e);
					throw e;
				}
			} else {
				IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] não deve conter espaços em branco!");
				logger.error(StringUtils.EMPTY, e);
				throw e;
			}
		} else {
			IllegalArgumentException e = new IllegalArgumentException("O tamanho do Campo Livre [ " + strCampoLivre + " ] deve ser igual a " + tamanhoCorreto + " e não ["+strCampoLivre.length()+"]!");
			logger.error(StringUtils.EMPTY, e);
			throw e;
		}
			
		return campoLivre;
	}

	/**
	 * <p>
	 * Devolve um <code>CampoLivre</code> de acordo com o Banco contido na conta do Cedente.
	 * </p> 
	 * <p>
	 * Caso exista implementação para o banco o retorno terá uma referência não nula.
	 * </p>
	 * 
	 * @param titulo
	 * 
	 * @return Uma referência para um CampoLivre.
	 * @throws NaoSuportadoBancoExecao 
	 * @throws NaoSuportadoCampoLivreExcecao 
	 */
	public static CampoLivre create(Boleto titulo) throws NaoSuportadoBancoExecao, NaoSuportadoCampoLivreExcecao {
		return AbstractCampoLivre.create(titulo);
	}
	
	/**
	 * Devolve um CampoLivre de acordo a partir de uma String.
	 * 
	 * @param strCampoLivre
	 * 
	 * @return Uma referência para um ICampoLivre.
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public static CampoLivre create(String strCampoLivre) {
		CampoLivre campoLivre = null;
		
		TextoUtil.checkNotNull(strCampoLivre);
		TextoUtil.checkNotBlank(strCampoLivre, "O Campo Livre não deve ser vazio!");
		
		strCampoLivre = StringUtils.strip(strCampoLivre); 
		
		if (strCampoLivre.length() == CampoLivre.TAMANHO_CAMPO_LIVRE) {
			if (StringUtils.remove(strCampoLivre, ' ').length() == CampoLivre.TAMANHO_CAMPO_LIVRE) {
				if (StringUtils.isNumeric(strCampoLivre)) {
					campoLivre = new CampoLivre() {
						private static final long serialVersionUID = -7592488081807235080L;

						Campo<String> campo = new Campo<String>(StringUtils.EMPTY,
								TAMANHO_CAMPO_LIVRE, Preenchedor.ZERO_ESQUERDA);

						public void read(String str) {
							campo.read(str);
						}

						public String write() {
							return campo.write();
						}
					};
					
					campoLivre.read(strCampoLivre);
					
				} else {
					IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] deve ser uma String numérica!");
					logger.error(StringUtils.EMPTY, e);
					throw e;
				}
			} else {
				IllegalArgumentException e = new IllegalArgumentException("O Campo Livre [ " + strCampoLivre + " ] não deve conter espaços em branco!");
				logger.error(StringUtils.EMPTY, e);
				throw e;
			}
		} else {
			IllegalArgumentException e = new IllegalArgumentException("O tamanho do Campo Livre [ " + strCampoLivre + " ] deve ser igual a 25 e não ["+strCampoLivre.length()+"]!");
			logger.error(StringUtils.EMPTY, e);
			throw e;
		}
			
		return campoLivre;
	}

}
