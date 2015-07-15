package br.com.infosolo.cobranca.boleto.guia;

import org.apache.commons.lang.StringUtils;

import br.com.infosolo.cobranca.boleto.Modulo;
import br.com.infosolo.cobranca.util.AbstractLinhaDeCampos;
import br.com.infosolo.cobranca.util.Campo;
import br.com.infosolo.comum.util.Logger;
import br.com.infosolo.comum.util.TextoUtil;

/**
 * 
 * 
 * @see br.com.nordestefomento.jrimum.bopepo.guia.CodigoDeBarras
 * 
 * @author Misael Barreto 
 * 
 * @since 0.3
 * 
 * @version 0.3
 */
public final class LinhaDigitavel extends AbstractLinhaDeCampos {
	private static final long serialVersionUID = -3834406645384269082L;
	
	private static Logger logger = new Logger(AbstractLinhaDeCampos.class);

	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 4;
	
	/**
	 * <p>
	 * Tamanho dos campos mais os espaços entre eles. <br/>
	 * 44 posições do código de barras <br/>
	 * + <font color="red">4 dígitos verificadores</font><br/>
	 * + 4 espaços em branco (entre cada campo e seu dígito verificador): <br/>
	 * + 3 espaços em branco (para separar os campos): <br/>
	 * Ex: 89610000001 <font size="4" color="red">8</font>
	 *     00000001011 <font size="4" color="red">6</font>
	 *     05449201004 <font size="4" color="red">3</font>
	 *     26011145220 <font size="4" color="red">7</font>
	 *     <br/>
 	 * </p>
	 */
	private static final Integer STRING_LENGTH = 55;

	/**
	 * 
	 */
	private Campo<InnerCampo1> innerCampo1;
	
	/**
	 * 
	 */
	private Campo<InnerCampo2> innerCampo2;
	
	/**
	 * 
	 */
	private Campo<InnerCampo3> innerCampo3;
	
	/**
	 * 
	 */
	private Campo<InnerCampo4> innerCampo4;


	/**
	 * <p>
	 * Cria uma linha digitável com a partir do código de barras passado.
	 * </p>
	 * 
	 * @param codigoDeBarras
	 * 
	 * @see br.com.nordestefomento.jrimum.bopepo.guia.CodigoDeBarras
	 * 
	 * @since 0.3
	 */
	LinhaDigitavel(CodigoDeBarras codigoDeBarras) {
		super(FIELDS_LENGTH,STRING_LENGTH);
		
		logger.debug("Instanciando Linha Digit�vel�vel");
		
		logger.debug("codigoDeBarra instance : "+codigoDeBarras);
		
		// Cada InnerCampo possui 2 campos internos, com um total de 
		// 13 caracteres ao final (11 dígitos do CB + " " + DV).
		innerCampo1 = new Campo<InnerCampo1>(new InnerCampo1(2,13),13);
		innerCampo2 = new Campo<InnerCampo2>(new InnerCampo2(2,13),13);
		innerCampo3 = new Campo<InnerCampo3>(new InnerCampo3(2,13),13);
		innerCampo4 = new Campo<InnerCampo4>(new InnerCampo4(2,13),13);
		
		add(innerCampo1);
		add(innerCampo2);
		add(innerCampo3);
		add(innerCampo4);
		
		this.innerCampo1.getValue().load(codigoDeBarras);
		this.innerCampo2.getValue().load(codigoDeBarras);
		this.innerCampo3.getValue().load(codigoDeBarras);
		this.innerCampo4.getValue().load(codigoDeBarras);
		
		logger.debug("Linha digit�vel�vel instanciada : " + this.write());
	}

	/**
	 * Escreve a linha digitável foramatada (com espaço entre os campos).
	 * 
	 * @see br.com.nordestefomento.jrimum.utilix.AbstractLineOfFields#write()
	 */
	@Override
	public String write(){
		StringBuilder lineOfFields = new StringBuilder(innerCampo1.write()).
			append(TextoUtil.ESPACO_BRANCO).
			append(innerCampo2.write()).
			append(TextoUtil.ESPACO_BRANCO).
			append(innerCampo3.write()).
			append(TextoUtil.ESPACO_BRANCO).
			append(innerCampo4.write());
		
		isConsistent(lineOfFields);
		return lineOfFields.toString();
	}

	private abstract class InnerCampo extends AbstractLinhaDeCampos {		
		private static final long serialVersionUID = -5345299360636304482L;
		
		private Integer numeroCampo;
		
		private InnerCampo(Integer fieldsLength, Integer stringLength, Integer numeroCampo) {
			super(fieldsLength, stringLength);
			this.numeroCampo = numeroCampo;
		}
		
		/**
		 * <p>
		 * 
		 * Aplicação do seguinte requisito da FEBRABAN: <br/>
		 * Em cada campo o dígito verificador deverá ser separado do conteúdo 
		 * restante através de um espaço em branco " ". <br/><br/>
		 * Exemplo: <br/>
		 * Campo não formatado: 896100000018 (Dígito Verificador = 8) <br/>
		 * Campo formatado:  89610000001 8
		 * </p>
		 * 
		 * @see br.com.nordestefomento.jrimum.utilix.AbstractLineOfFields#write()
		 */
		@Override
		public String write(){
			StringBuilder lineOfFields = new StringBuilder(StringUtils.EMPTY);
			
			for(Campo<?> field : this)
				lineOfFields.append(field.write());
			
			lineOfFields.insert(11, " ");
			isConsistent(lineOfFields);

			return lineOfFields.toString();
		}
		
		/**
		 * @param CodigoDeBarras codigoDeBarras
		 */
		public void load(CodigoDeBarras codigoDeBarras){
			logger.debug("Compondo campo " + this.numeroCampo + " da Linha Digit�vel");

			// Obtendo o trecho do código de barras que servirá de base para
			// a montagem da linha digitável. Casa classe filha definirá
			// que trecho é esse.
			String trechoCodigoDeBarras = getTrechoCodigoDeBarras(codigoDeBarras);
			add(  new Campo<String>(trechoCodigoDeBarras, 11)  );
			add(  new Campo<Integer>(calcularDVGuiaLinhaDigitavel(trechoCodigoDeBarras, 
					codigoDeBarras.getModuloParaCalculoDV()), 1)  );
			
			logger.debug("Digito verificador do Field " + this.numeroCampo + " da Linha Digit�vel: "+ get(1).getValue());

			logger.debug("Field " + this.numeroCampo + " da Linha Digit�vel�vel composto : "+ this.write());
		}
		
		protected abstract String getTrechoCodigoDeBarras(CodigoDeBarras codigoDeBarras);
		
		private int calcularDVGuiaLinhaDigitavel(String numeroStr, Modulo modulo) throws IllegalArgumentException {
			int dv = 0;
			int resto = 0;

			if (StringUtils.isNotBlank(numeroStr) && StringUtils.isNumeric(numeroStr) && (numeroStr.length() == 11)) {
				resto = modulo.calcule(numeroStr);
				
				if (modulo.getMod() == TipoDeModulo.MODULO11.valor()) {
					// Seguindo as especificações da FEBRABAN, caso o resto seja
					// (0), (1) ou (10), será atribuído (1) ao digito verificador.			
					if ((resto == 0) || (resto == 1) || (resto == 10))
						dv = 1;
					// Caso contrário, dv = 11 - resto.
					else
						dv = modulo.valor() - resto;
				}
				
				else if (modulo.getMod() == TipoDeModulo.MODULO10.valor()) {
					// Seguindo as especificações da FEBRABAN, caso o resto seja
					// (0) ou (10), será atribuido o valor zero.
					if (  (resto == 0) || (resto == 10)  )
						dv = 0;
					// Caso contrário, dv = 10 - resto.
					else
						dv = modulo.valor() - resto;
				}			
			} else
				throw new IllegalArgumentException(
						"O campo [ " + numeroStr + " ] da linha digitável deve " +
						"conter apenas números, com exatamento 11 dígitos, para que " +
						"o cálculo do dígito verificador possa ser executado !");

			return dv;
		}
		
	}
	
	
	private class InnerCampo1 extends InnerCampo {
		private static final long serialVersionUID = -780242445964556753L;

		private InnerCampo1(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength, 1);
		}

		@Override
		protected String getTrechoCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
			// Posição 01 a 11 do código de barras
			return codigoDeBarras.write().substring(0, 11);
		}
	}
	
	
	
	private class InnerCampo2 extends InnerCampo {
		private static final long serialVersionUID = 382377306835578506L;

		private InnerCampo2(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength, 2);
		}

		@Override
		protected String getTrechoCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
			// Posição 12 a 22 do código de barras
			return codigoDeBarras.write().substring(11, 22);
		}
	}	
	
	private class InnerCampo3 extends InnerCampo {
		private static final long serialVersionUID = -5589288171927030864L;

		private InnerCampo3(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength, 3);
		}

		@Override
		protected String getTrechoCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
			// Posição 23 a 33 do código de barras
			return codigoDeBarras.write().substring(22, 33);
		}
	}		
	
	private class InnerCampo4 extends InnerCampo {
		private static final long serialVersionUID = 7559169854185810900L;

		private InnerCampo4(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength, 4);
		}

		@Override
		protected String getTrechoCodigoDeBarras(CodigoDeBarras codigoDeBarras) {
			// Posição 34 a 44 do código de barras
			return codigoDeBarras.write().substring(33, 44);
		}
	}		
	
	@Override
	public String toString() {
		return TextoUtil.toString(this);
	}
}
