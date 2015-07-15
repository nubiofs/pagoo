package br.com.infosolo.cobranca.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

import br.com.infosolo.cobranca.boleto.Modulo;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.comum.util.DataUtil;

/**
 * <p>
 * Servicos utilitarios do universo bancario, como por exemplo calcular o fator
 * de vencimento de boletos.</code>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 */
public class BancoUtil implements Serializable {
	private static final long serialVersionUID = -9041865935492749542L;

	private static final String MENSAGEM_ERRO_CODIGO_BANCO = "O c�digo de compensa��o do banco deve ser um n�mero entre 1 e 999.";
	
	/**
	 * <p>
	 * Data base para o calculo do fator de vencimento fixada em 07/10/1997 pela
	 * FEBRABAN.
	 * </p>
	 */
	private static final Date DATA_BASE_DO_FATOR_DE_VENCIMENTO = new GregorianCalendar(
			1997, Calendar.OCTOBER, 7).getTime();

	/**
	 *<p>
	 * Data maxima alcancada pelo fator de vencimento com base fixada em
	 * 07/10/1997.
	 * </p>
	 */
	private static final Date DATA_LIMITE_DO_FATOR_DE_VENCIMENTO = new GregorianCalendar(
			2025, Calendar.FEBRUARY, 21).getTime();

	/**
	 * <p>
	 * Calcula o fator de vencimento a partir da subtracao entre a DATA DE
	 * VENCIMENTO de um titulo e a DATA BASE fixada em 07/10/1997.
	 * </p>
	 * 
	 * <p>
	 * O fator de vencimento nada mais e que um referencial numerico de 4
	 * digitos que representa a quantidade de dias decorridos desde a data base
	 * (07/10/1997) ate a data de vencimento do titulo. Ou seja, a diferenca em
	 * dias entre duas datas.
	 * </p>
	 * 
	 * <p>
	 * Exemplos:
	 * </p>
	 * <ul type="circule"> <li>07/10/1997 (Fator = 0);</li> <li>03/07/2000
	 * (Fator = 1000);</li> <li>05/07/2000 (Fator = 1002);</li> <li>01/05/2002
	 * (Fator = 1667);</li> <li>21/02/2025 (Fator = 9999).</li> </ul>
	 * 
	 * <p>
	 * Funcionamento:
	 * </p>
	 * 
	 * <ul type="square"> <li>Caso a data de vencimento seja anterior a data
	 * base (Teoricamente fator negativo), uma excecao do tipo
	 * IllegalArgumentException sera lancada.</li> <li>A data limite para o
	 * calculo do fator de vencimento e 21/02/2025 (Fator de vencimento = 9999).
	 * Caso a data de vencimento seja posterior a data limite, uma excecao do
	 * tipo IllegalArgumentException sera lancada.</li> </ul>
	 * 
	 * <p>
	 * <strong>ATENCAO</strong>, esse calculo se refere a titulos em cobranca,
	 * ou melhor: BOLETOS. Desta forma, lembramos que a DATA BASE e uma norma da
	 * FEBRABAN. Essa norma diz que todos os boletos emitidos a partir de 1� de
	 * setembro de 2000 (primeiro dia util = 03/07/2000 - SEGUNDA) devem seguir
	 * esta regra de calculo para compor a informacao de vencimento no codigo de
	 * barras. Portanto, boletos no padrao FEBRABAN quando capturados por
	 * sistemas da rede bancaria permitem que se possa realizar a operacao
	 * inversa, ou seja, adicionar a data base o fator de vencimento capturado.
	 * Obtendo entao a data de vencimento deste boleto.
	 * </p>
	 * 
	 * @param dataVencimento
	 *            data de vencimento de um titulo
	 * @return fator de vencimento calculado
	 * @throws IllegalArgumentException
	 * 
	 * @since 0.2
	 */

	public static int calcularFatorDeVencimento(Date dataVencimento)
			throws IllegalArgumentException {

		Date dataVencTruncada = null;
		int fator;

		if (dataVencimento == null) {
			throw new IllegalArgumentException("Impossivel realizar o calculo do fator"
							+ " de vencimento de uma data nula.");
		} else {
			dataVencTruncada = DateUtils.truncate(dataVencimento, Calendar.DATE);
			if (dataVencTruncada.before(DATA_BASE_DO_FATOR_DE_VENCIMENTO)
					|| dataVencTruncada.after(DATA_LIMITE_DO_FATOR_DE_VENCIMENTO)) {
				throw new IllegalArgumentException(
						"Para o calculo do fator de"
								+ " vencimento se faz necessario informar uma data entre"
								+ " "
								+ DataUtil.formatarData(DATA_BASE_DO_FATOR_DE_VENCIMENTO)
								+ " e "
								+ DataUtil.formatarData(DATA_LIMITE_DO_FATOR_DE_VENCIMENTO));
			} else {
				fator = (int) DataUtil.calcularDiferencaEmDias(
						DATA_BASE_DO_FATOR_DE_VENCIMENTO, dataVencTruncada);
			}
		}

		return fator;
	}
	
	/**
	 * <p>
	 * Calcula o dígito verificador para código de compensação passado.
	 * </p>
	 * 
	 * @param numero
	 * 
	 * @return int digito
	 * 
	 * @since 0.2
	 * 
	 */
	public static int calcularDVBanco(long numero) {
		int dv = -1;

		if (!(numero >= 1 && numero <= 999)) {
			throw new IllegalArgumentException(MENSAGEM_ERRO_CODIGO_BANCO);
		}

		int soma = Modulo.calculeSomaSequencialMod11(String.valueOf(numero), 2, 9);
		soma *= 10;
		dv = soma % 11;
		dv = (dv == 10) ? 0 : dv;

		return dv;
	}

	
	/**
	 * Retorna o código do banco junto ao BACEN com dígito verificador no formato XXX-X.
	 * @return
	 */
	public static String retornarCodigoBancoBACEN(Banco banco) {
		return String.format("%03d-%1d", banco.getCodigo(), BancoUtil.calcularDVBanco(banco.getCodigo()));
	}

}
