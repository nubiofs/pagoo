package br.com.infosolo.cobranca.util;

import com.lowagie.text.Rectangle;

/**
 * 
 * <p>
 * Classe adapter para facilitar as operacoes com os fields pdf com a lib iText.
 * </p>
 * 
 */
public class RetanguloPDF extends Rectangle {
	private int page;

	/**
	 * For each of this groups the values are: [page, llx, lly, urx, ury].
	 */
	public RetanguloPDF(float[] positions) {
		super(positions[1], positions[2], positions[3], positions[4]);
		page = (int) positions[0];
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public RetanguloPDF(float arg0, float arg1, float arg2, float arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RetanguloPDF(float arg0, float arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public RetanguloPDF(Rectangle arg0) {
		super(arg0);
	}

	/**
	 * @return page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return llx - lower left x
	 */
	public float getLowerLeftX() {
		return this.llx;
	}

	/**
	 * @return lly - lower left y
	 */
	public float getLowerLeftY() {
		return lly;
	}

	/**
	 * 
	 * @return urx - upper right x
	 */
	public float getUpperRightX() {
		return urx;
	}

	/**
	 * @return ury - upper right y
	 */
	public float getUpperRightY() {
		return ury;
	}

}
