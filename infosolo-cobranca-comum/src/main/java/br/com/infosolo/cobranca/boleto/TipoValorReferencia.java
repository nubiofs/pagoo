package br.com.infosolo.cobranca.boleto;


/**
 * @author misael
 *
 */
public enum TipoValorReferencia {
	
	VALOR_COBRADO_EM_REAL_COM_DV_MODULO_10(6, new Modulo(Modulo.MODULO10)),
	QUANTIDADE_DE_MOEDA_COM_DV_MODULO_10(7, new Modulo(Modulo.MODULO10)),
	VALOR_COBRADO_EM_REAL_COM_DV_MODULO_11(8, new Modulo(Modulo.MODULO11)),
	QUANTIDADE_DE_MOEDA_COM_DV_MODULO_11(9, new Modulo(Modulo.MODULO11));
	
	private Integer codigo;
	private Modulo modulo;
	
	/**
	 * @param codigo
	 * @param nome
	 */
	private TipoValorReferencia(Integer codigo, Modulo modulo) {
		this.codigo = codigo;
		this.modulo = modulo;
	}

	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @return the modulo
	 */
	public Modulo getModulo() {
		return modulo;
	}	
	
	public static TipoValorReferencia findByCodigo(Integer codigo) {
		TipoValorReferencia tipoValorReferenciaRetorno = null;
		
		TipoValorReferencia[] tipoValorReferenciaArray = TipoValorReferencia.values();
		for (int i = 0; i < tipoValorReferenciaArray.length; i++) {
			if (tipoValorReferenciaArray[i].codigo.equals(codigo)) {
				tipoValorReferenciaRetorno = tipoValorReferenciaArray[i];
				break;
			}
				
		}
		
		return tipoValorReferenciaRetorno;
	}	
}
