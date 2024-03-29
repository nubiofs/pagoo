package br.com.infosolo.cobranca.enumeracao;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Representa as moedas existentes que fazem parte do universo de um titulo, segundo a FEBRABAN.
 * </p>
 * 
 */
public enum TipoMoeda {

	/**
	 * Padrao FEBRABAN
	 */
	
	DOLAR_AMERICANO_COMERCIAL_VENDA(2),
	DOLAR_AMERICANO_TURISMO_VENDA(3),
	ITRD(4),
	IDTR(5),
	UFIR_DIARIA(6),
	UFIR_MENSAL(7),
	FAJ_TR(8),
	REAL(9),
	TR(10),
	IGPM(11),
	CDI(12),
	PERCENTUAL_DO_CDI(13),
	EURO(14);

	private int codigo;
	
	private TipoMoeda(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return this.codigo;
	}

	/**
	 * Retorna a sigla da moeda.
	 * @return
	 */
	public String getSigla() {
		String s = StringUtils.EMPTY;
		
		switch (this) {
			case REAL:
				s = "R$";
				break;
		
			default:
				s += this.getCodigo();
		}
		
		return s;
	}
}
