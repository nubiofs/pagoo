package br.com.infosolo.comum.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;

public class DataUtil {
	private static Calendar CALENDAR = Calendar.getInstance();

    public static final String FORMATO_DATA_DD_MM_AAAA_BARRA = "dd/MM/yyyy";
    public static final String FORMATO_DATA_AAAA_MM_DD_BARRA = "yyyy/MM/dd";
    public static final String FORMATO_DATA_DD_MM_AAAA = "ddMMyyyy";
    public static final String FORMATO_DATA_AAAA_MM_DD = "yyyyMMdd";
    public static final String FORMATO_HORA_HH_MM_SS = "HHmmss";
    public static final String FORMATO_DATA_HORA_COMPLETO = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_HORA_SIMPLES = "HH:mm";
    public static final String FORMATO_HORA_COMPLETO = "HH:mm:ss";
    public static final String FORMATO_DATA_SQL_TRACO = "yyyy-MM-dd";
    
    /**
     * Data de Arquivo Nula
     */
    public static final String DATA_NULA_ARQUIVO = "00000000";
    
    private final static Locale ptBR = new Locale("pt", "BR");

    /**
     * Retorna uma String com a data a partir do pattern informado.
     * 
     * @author eandrade
     * @param data
     *            Uma data para ser formatada.
     * @param pattern
     *            O pattern no qual se deseja o formato da data.
     * @return Uma String com a data a partir do pattern informado. Se data ou
     *         pattern for null, uma String vazia será retornada.
     */
    public static String formatarData(Date data, String pattern) {
        String stData = "";
        
        if ((data != null) && (pattern != null)) {
            DateFormat format = new SimpleDateFormat(pattern);
            stData = format.format(data);
        }

        return stData;
    }
    
    /**
     * Formata a data informada no formato de data e hora completo
     * @param data
     * @return
     */
    public static String formatarDataHora(Date data) {
	   return formatarData(data, FORMATO_DATA_HORA_COMPLETO);
    }

    /**
     * Formata uma data para arquivo no padrao ddMMyyyy.
     * @param data
     * @return
     */
    public static String formatarDataArquivo(Date data) {
    	if (data != null)
    		return formatarData(data, FORMATO_DATA_DD_MM_AAAA);
    	else
    		return DATA_NULA_ARQUIVO;
    }
    
    /**
     * Formata uma data de arquivo inserindo as barras no formato "dd/MM/yyyy"
     * @param data
     * @return
     */
    public static Date formatarDataArquivoStr(String data) {
    	StringBuffer buffer = new StringBuffer(data);
    	buffer.insert(2, '/');
    	buffer.insert(5, '/');
    	Date dataArquivo = null;
		try {
			if (!data.equals("00000000")) {
				dataArquivo = getData(buffer.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return dataArquivo;
    }
    
    /**
     * Formata a data do SERPRO
     * @param data
     * @return
     */
    public static String formatarDataSerpro(String data) {
    	StringBuffer buffer = new StringBuffer(data);
    	buffer.insert(2, '/');
    	buffer.insert(5, '/');
    	return buffer.toString();
    }
    

    /**
     * Formata uma data de arquivo inserindo as barras no formato "yyyy/MM/dd"
     * @param data
     * @return
     */
    public static Date formatarDataArquivoStr2(String data) {
    	StringBuffer buffer = new StringBuffer(data);
    	buffer.insert(4, '/');
    	buffer.insert(7, '/');
    	Date dataArquivo = null;
		if (!data.equals("00000000")) {
			dataArquivo = getData(buffer.toString(), FORMATO_DATA_AAAA_MM_DD_BARRA);
		}
    	return dataArquivo;
    }

    /**
     * Formata uma hora para arquivo no padrão HHmmss
     * @param data
     * @return
     */
    public static String formatarHoraArquivo(Date data) {
    	return formatarData(data, FORMATO_HORA_HH_MM_SS);
    }

    /**
     * Retorna a data informada em formato de data curto.
     * @param data
     * @return
     * @throws ParseException
     */
    public static Date getData(String data) throws ParseException {  
    	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, ptBR);  
    	return df.parse(data);  
    } 

    /**
     * Retorna um objeto Date pelo tempo informado em milisegundos
     * @param data
     * @return
     */
    public static Date getData(long data) {
    	return new Date(data);
    } 

    /**
     * Retorna a data no formato desejado.
     * @param data
     * @param formato
     * @return
     */
    public static Date getData(String data, String formato) {  
    	DateFormat formatter = new SimpleDateFormat(formato);
    	Date retorno = null;
    	try {
    		retorno = formatter.parse(data);
		} catch (ParseException e) {
			//e.printStackTrace();
			System.err.println("Erro de conversao de data: " + e.toString());
		}  
		return retorno;
    } 

    /**
     * Retorna a hora informada no objeto Date
     * @param hora
     * @return
     */
    public static Date getHora(String hora) {
    	return getData(hora, FORMATO_HORA_COMPLETO);
    }
    
    /**
     * Retorna a data com hora no formato completo.
     * @param data
     * @return
     */
    public static Date getDataHora(String data) {
    	return getData(data, FORMATO_DATA_HORA_COMPLETO);
    }
    
	/**
	 * Transform a date in a long to a GregorianCalendar
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar longToGregorian(long date) {
		DatatypeFactory dataTypeFactory;
		try {
			dataTypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date);
		return dataTypeFactory.newXMLGregorianCalendar(gc);
	}

	/**
	 * Transform a date in Date to XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar dateToGregorian(Date date) {
		return longToGregorian(date.getTime());
	}
	
	/**
	 * Transforma um XMLGregorianCalendar em um Date.
	 * @param calendar
	 * @return
	 */
	public static Date gregorianToDate(XMLGregorianCalendar calendar) {
		Date data = calendar.toGregorianCalendar().getTime();
		return data;
	}

    /**
     * Retorna uma String com a data a partir do pattern informado.
     * 
     * @author eandrade
     * @param data
     *            Uma data para ser formatada.
     * @param pattern
     *            O pattern no qual se deseja o formato da data.
     * @return Uma String com a data a partir do pattern informado. Se data ou
     *         pattern for null, uma String vazia será retornada.
     */
    public static String formatar(Date data, String pattern) {
        String stData = "";
        
        if ((data != null) && (pattern != null)) {
            DateFormat format = new SimpleDateFormat(pattern);
            stData = format.format(data);
        }

        return stData;
    }

    public static String getDataAtual(){
    	return formatar(new Date(), FORMATO_DATA_DD_MM_AAAA_BARRA);
    }
    
    /**
     * Formata uma data com o padrão dd/MM/yyyy.
     * @param data
     * @return
     */
    public static String formatarData(Date data) {
    	return formatar(data, FORMATO_DATA_DD_MM_AAAA_BARRA);
    }
    
	/**
	 * <p>
	 * Calcula a diferença de dias entre duas datas. O resultado é modular, ou
	 * seja, maior ou igual a zero, logo a data final não precisa ser
	 * necessariamente maior que a data inicial.
	 * </p>
	 * 
	 * @param dataInicial
	 *            - data inicial do intervalo.
	 * @param dataFinal
	 *            - data final do intervalo.
	 * @return número(módulo) de dias entre as datas.
	 * 
	 * @throws IllegalArgumentException
	 *             Caso pelo menos uma das duas datas seja <code>null</code>.
	 * @since 0.2
	 */
	public static long calcularDiferencaEmDias(final Date dataInicial, final Date dataFinal) {
		long fator = 0;
		Date dataInicialTruncada, dataFinalTruncada;

		if (dataInicial != null && dataFinal != null) {
			dataInicialTruncada = DateUtils.truncate(dataInicial, Calendar.DATE);
			dataFinalTruncada = DateUtils.truncate(dataFinal, Calendar.DATE);

			fator = ((dataFinalTruncada.getTime() - dataInicialTruncada.getTime()) / DateUtils.MILLIS_PER_DAY);

			if (fator < 0) {
				fator *= -1;
			}
		} else {
			throw new IllegalArgumentException("A data inicial [" + dataInicial
					+ "] e a data final [" + dataFinal + "] "
					+ "não podem ter valor 'null'.");
		}

		return fator;
	}

    /**
     * Returns the last millisecond of the specified date.
     *
     * @param date Date to calculate end of day from
     * @return Last millisecond of <code>date</code>
     */
    public static Date endOfDay(Date date) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MILLISECOND, 999);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MINUTE, 59);
            return calendar.getTime();
        }
    }


    /**
     * Returns a new Date with the hours, milliseconds, seconds and minutes
     * set to 0.
     *
     * @param date Date used in calculating start of day
     * @return Start of <code>date</code>
     */
    public static Date startOfDay(Date date) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            return calendar.getTime();
        }
    }

     /**
     * Returns day in millis with the hours, milliseconds, seconds and minutes
     * set to 0.
     *
     * @param date long used in calculating start of day
     * @return Start of <code>date</code>
     */
    public static long startOfDayInMillis(long date) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Returns the last millisecond of the specified date.
     *
     * @param date long to calculate end of day from
     * @return Last millisecond of <code>date</code>
     */
    public static long endOfDayInMillis(long date) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MILLISECOND, 999);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MINUTE, 59);
            return calendar.getTimeInMillis();
        }
    }


    /**
     * Returns the day after <code>date</code>.
     *
     * @param date Date used in calculating next day
     * @return Day after <code>date</code>.
     */
    public static Date nextDay(Date date) {
        return new Date(addDays(date.getTime(), 1));
    }

    /**
     * Adds <code>amount</code> days to <code>time</code> and returns
     * the resulting time.
     *
     * @param time Base time
     * @param amount Amount of increment.
     * 
     * @return the <var>time</var> + <var>amount</var> days
     */
    public static long addDays(long time, int amount) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.DAY_OF_MONTH, amount);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Returns the day after <code>date</code>.
     *
     * @param date Date used in calculating next day
     * @return Day after <code>date</code>.
     */
    public static long nextDay(long date) {
        return addDays(date, 1);
    }

    /**
     * Returns the week after <code>date</code>.
     *
     * @param date Date used in calculating next week
     * @return week after <code>date</code>.
     */
    public static long nextWeek(long date) {
        return addDays(date, 7);
    }


    /**
     * Returns the number of days difference between <code>t1</code> and
     * <code>t2</code>.
     *
     * @param t1 Time 1
     * @param t2 Time 2
     * @param checkOverflow indicates whether to check for overflow
     * @return Number of days between <code>start</code> and <code>end</code>
     */
    public static int getDaysDiff(long t1, long t2, boolean checkOverflow) {
        if (t1 > t2) {
            long tmp = t1;
            t1 = t2;
            t2 = tmp;
        }
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(t1);
            int delta = 0;
            while (calendar.getTimeInMillis() < t2) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                delta++;
            }
            if (checkOverflow && (calendar.getTimeInMillis() > t2)) {
                delta--;
            }
            return delta;
        }
    }

   /**
     * Returns the number of days difference between <code>t1</code> and
     * <code>t2</code>.
     *
     * @param t1 Time 1
     * @param t2 Time 2
     * @return Number of days between <code>start</code> and <code>end</code>
     */
      public static int getDaysDiff(long t1, long t2) {
       return  getDaysDiff(t1, t2, true);
    }

    /**
     * Check, whether the date passed in is the first day of the year.
     *
     * @param date date to check in millis
     * @return <code>true</code> if <var>date</var> corresponds to the first
     *         day of a year
     * @see Date#getTime() 
     */
    public static boolean isFirstOfYear(long date) {
        boolean ret = false;
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            int currentYear = calendar.get(Calendar.YEAR);
            // Check yesterday
            calendar.add(Calendar.DATE,-1);
            int yesterdayYear = calendar.get(Calendar.YEAR);
            ret = (currentYear != yesterdayYear);
        }
        return ret;
    }

    /**
     * Check, whether the date passed in is the first day of the month.
     *
     * @param date date to check in millis
     * @return <code>true</code> if <var>date</var> corresponds to the first
     *         day of a month
     * @see Date#getTime() 
     */
    public static boolean isFirstOfMonth(long date) {
        boolean ret = false;
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            int currentMonth = calendar.get(Calendar.MONTH);
            // Check yesterday
            calendar.add(Calendar.DATE,-1);
            int yesterdayMonth = calendar.get(Calendar.MONTH);
            ret =  (currentMonth != yesterdayMonth);
        }
        return ret;     
    }


    /**
     * Returns the day before <code>date</code>.
     *
     * @param date Date used in calculating previous day
     * @return Day before <code>date</code>.
     */
    public static long previousDay(long date) {
        return addDays(date, -1);
    }

    /**
     * Returns the week before <code>date</code>.
     *
     * @param date Date used in calculating previous week
     * @return week before <code>date</code>.
     */
    public static long previousWeek(long date) {
        return addDays(date, -7);
    }


    /**
     * Returns the first day before <code>date</code> that has the
     * day of week matching <code>startOfWeek</code>.  For example, if you
     * want to find the previous monday relative to <code>date</code> you
     * would call <code>getPreviousDay(date, Calendar.MONDAY)</code>.
     *
     * @param date Base date
     * @param startOfWeek Calendar constante correspoding to start of week.
     * @return start of week, return value will have 0 hours, 0 minutes,
     *         0 seconds and 0 ms.
     * 
     */
    public static long getPreviousDay(long date, int startOfWeek) {
        return getDay(date, startOfWeek, -1);
    }

    /**
     * Returns the first day after <code>date</code> that has the
     * day of week matching <code>startOfWeek</code>.  For example, if you
     * want to find the next monday relative to <code>date</code> you
     * would call <code>getPreviousDay(date, Calendar.MONDAY)</code>.
     *
     * @param date Base date
     * @param startOfWeek Calendar constante correspoding to start of week.
     * @return start of week, return value will have 0 hours, 0 minutes,
     *         0 seconds and 0 ms.
     * 
     */
    public static long getNextDay(long date, int startOfWeek) {
        return getDay(date, startOfWeek, 1);
    }

    private static long getDay(long date, int startOfWeek, int increment) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            // Normalize the view starting date to a week starting day
            while (day != startOfWeek) {
                calendar.add(Calendar.DATE, increment);
                day = calendar.get(Calendar.DAY_OF_WEEK);
            }
            return startOfDayInMillis(calendar.getTimeInMillis());
        }
    }

    /**
     * Returns the previous month.
     * 
     * @param date Base date
     * @return previous month
     */
    public static long getPreviousMonth(long date) {
        return incrementMonth(date, -1);
    }

    /**
     * Returns the next month.
     * 
     * @param date Base date
     * @return next month
     */
    public static long getNextMonth(long date) {
        return incrementMonth(date, 1);
    }

    private static long incrementMonth(long date, int increment) {
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            calendar.add(Calendar.MONTH, increment);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Returns the date corresponding to the start of the month.
     *
     * @param date Base date
     * @return Start of month.
     */
    public static long getStartOfMonth(long date) {
        return getMonth(date, -1);
    }

    /**
     * Returns the date corresponding to the end of the month.
     *
     * @param date Base date
     * @return End of month.
     */
    public static long getEndOfMonth(long date) {
        return getMonth(date, 1);
    }

    private static long getMonth(long date, int increment) {
		long result;
        Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            if (increment == -1) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                result = startOfDayInMillis(calendar.getTimeInMillis());
            } else {
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.add(Calendar.MILLISECOND, -1);
                result = calendar.getTimeInMillis();
            }
        }
		return result;
    }

    /**
     * Returns the day of the week.
     *
     * @param date date
     * @return day of week.
     */
    public static int getDayOfWeek(long date) {
       Calendar calendar = CALENDAR;
        synchronized(calendar) {
            calendar.setTimeInMillis(date);
            return (calendar.get(Calendar.DAY_OF_WEEK));
        }
    }

}
