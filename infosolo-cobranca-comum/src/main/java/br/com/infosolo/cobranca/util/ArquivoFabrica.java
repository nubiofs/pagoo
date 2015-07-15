package br.com.infosolo.cobranca.util;

import java.lang.reflect.Field;
import java.util.TreeMap;

import br.com.infosolo.cobranca.dominio.arquivo.Registro;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroCabecalhoLote;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheA150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheG150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoE;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoP;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoQ;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoT;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheSegmentoU;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroDetalheZ150;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeArquivo;
import br.com.infosolo.cobranca.dominio.arquivo.RegistroRodapeLote;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;
import br.com.infosolo.cobranca.enumeracao.TipoArquivo;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.comum.util.Logger;

public class ArquivoFabrica {
	private static Logger logger = new Logger(ArquivoFabrica.class.getName());

	/**
	 * Calcula o tipo de registro e retorna dependendo do conteúdo da linha.
	 * @param linha
	 * @return
	 */
	public static Class obterTipoRegistro(String linha, TipoArquivo tipoArquivo) {
		Class tipoClasse = null;
		
		if (linha != null) {
			if (tipoArquivo == TipoArquivo.CNAB240) {
				char tipo = linha.charAt(7);
				
				switch (tipo) {
					case '0':
						tipoClasse = RegistroCabecalhoArquivo.class;
						break;
		
					case '1':
						tipoClasse = RegistroCabecalhoLote.class;
						break;
		
					case '3':
						char segmento = linha.charAt(13);
						
						switch (segmento) {
							case 'P':
								tipoClasse = RegistroDetalheSegmentoP.class;
								break;
		
							case 'Q':
								tipoClasse = RegistroDetalheSegmentoQ.class;
								break;
		
							case 'T':
								tipoClasse = RegistroDetalheSegmentoT.class;
								break;
		
							case 'U':
								tipoClasse = RegistroDetalheSegmentoU.class;
								break;

							case 'E':
								tipoClasse = RegistroDetalheSegmentoE.class;
								break;
						}
						break;
						
					case '5':
						tipoClasse = RegistroRodapeLote.class;
						break;
					
					case '9':
						tipoClasse = RegistroRodapeArquivo.class;
						break;
				}
			}
			else if (tipoArquivo == TipoArquivo.FEBRABAN150) {
				char tipo = linha.charAt(0);
				
				switch (tipo) {
					case 'A':
						tipoClasse = RegistroDetalheA150.class;
						break;
		
					case 'G':
						tipoClasse = RegistroDetalheG150.class;
						break;

					case 'Z':
						tipoClasse = RegistroDetalheZ150.class;
						break;
				}				
			}
		}
		
		return tipoClasse;
	}
	
	/**
	 * Gera o Dominio para o tipo especificado usando dados da linha infomada.
	 * @param classType
	 * @param linha
	 * @return
	 * @throws CobrancaExcecao
	 */
	public static Object gerarDominio(Class classType, String linha) throws CobrancaExcecao {
		Class classe = classType;
        Object objeto = null;
        
		if (linha == null || linha.length() == 0) {
			return null;
		}

		try {
        	objeto = classe.newInstance();
        	Field[] fields = classe.getFields();
        	
        	for (int i = 0; i < fields.length; i++) {
        		Field field = fields[i];
        		Object value = null;
        		String position = field.getAnnotation(Posicao.class).value();
        		String type = field.getType().getName();
        		int inicio = Integer.valueOf(position.substring(0, position.indexOf(',')));
        		int fim = Integer.valueOf(position.substring(position.indexOf(',') + 1, position.length()));
        		String str = null;
        		
        		try {
        			str = linha.substring(inicio - 1, fim).trim();
        		} catch (IndexOutOfBoundsException e) {
					
				}
        		
        		if (str != null) {
	        		if (isString(type)) {
	                    value = new String(str);
	            		field.set(objeto, value);
	        		}
	        		else if (isChar(type)) {
	                    if (str != null && !str.equals("")) {
	                        value = new Character(str.charAt(0));
	
	                    } else {
	                        value = new Character(' ');
	
	                    }
	            		field.set(objeto, value);
	                }
	        		else if (isLong(type)) {
	                    value = new Long(str);
	            		field.set(objeto, value);
	        		}
	        		else if (isInteger(type)) {
	                    value = new Integer(str);
	            		field.set(objeto, value);
	        		}
	        		else if (isByte(type)) {
	                    value = new Byte(str);
	            		field.set(objeto, value);
	        		}
	        		else if (isDouble(type)) {
	        			if (str.equals(""))
	        				value = 0.00;
	        			else
	        				value = new Double(str).doubleValue() / 100;
	            		field.set(objeto, value);
	        		}
        		}
        		//System.out.println("\nIndex: " + i);
        		//System.out.println("Posicao: " + position);
        		//System.out.println(field.getName() + " = '" + (field.get(objeto) != null ? field.get(objeto).toString() : "<NULL>") + "'");
        		//System.out.format("Tipo: %s%n", field.getType());
        		
        	}
	   	} catch (Exception e) {
           throw new CobrancaExcecao(e);
	   	}
        return objeto;
	}

	/**
	 * Gera a linha para gravacao no arquivo dependendo do registro informado.
	 * @param registro
	 * @param tipoArquivo
	 * @return
	 * @throws CobrancaExcecao
	 */
	@SuppressWarnings("rawtypes")
	public static String gerarLinha(Registro registro, TipoArquivo tipoArquivo) throws CobrancaExcecao {
		String linha = null;
		Class classe = registro.getClass();
        TreeMap<Integer, String> tabela = new TreeMap<Integer, String>();
        
		try {
	        Object objeto = registro;
        	Field[] fields = classe.getFields();
        	
        	for (int i = 0; i < fields.length; i++) {
        		Field field = fields[i];
        		Object value = null;
        		String position = field.getAnnotation(Posicao.class).value();
        		String type = field.getType().getName();
        		int inicio = Integer.valueOf(position.substring(0, position.indexOf(',')));
        		int fim = Integer.valueOf(position.substring(position.indexOf(',') + 1, position.length()));
        		int tamanho = fim - inicio + 1;
        		String strFormatado = "";

        		//System.out.println("\nIndex: " + i);
        		//System.out.println("Posicao: " + position);
        		//System.out.print(field.getName() + " = '");

        		if (isString(type)) {
        			if (field.get(objeto) != null)
        				value = field.get(objeto).toString();
        			else
        				value = ""; 
                    strFormatado = String.format("%-" + tamanho + "s", value);
                    strFormatado = strFormatado.substring(0, tamanho);
            		tabela.put(inicio, strFormatado);
        		}
        		else if (isChar(type)) {
                    if (field.get(objeto) != null) {
        				value = String.valueOf(field.getChar(objeto)).trim();
        				if (value.equals("")) value = new String(" ");
                    } else {
                        value = new String(" ");
                    }
                    strFormatado = String.valueOf(value);
            		tabela.put(inicio, strFormatado);
                }
        		else if (isLong(type)) {
                    if (field.get(objeto) != null) {
        				value = field.getLong(objeto);
                    } else {
                        value = 0L;
                    }
                    strFormatado = String.format("%0" + tamanho + "d", value);
            		tabela.put(inicio, strFormatado);
        		}
        		else if (isInteger(type) || isByte(type)) {
                    if (field.get(objeto) != null) {
        				value = field.getInt(objeto);
                    } else {
                        value = 0;
                    }
                    strFormatado = String.format("%0" + tamanho + "d", value);
            		tabela.put(inicio, strFormatado);
        		}
        		else if (isDouble(type)) {
                    if (field.get(objeto) != null) {
        				value = field.getDouble(objeto) * 100;
                    } else {
                        value = 0.0;
                    }
                    strFormatado = String.format("%0" + tamanho + ".0f", value);
            		tabela.put(inicio, strFormatado);
        		}

        		//System.out.println(strFormatado + "'");
        		//System.out.format("Tipo: %s%n", field.getType());
        	}
        	
        	// Monta a string de linha
        	linha = "";
        	for (String item : tabela.values()) {
				linha = linha + item;
				//System.out.println("-> '" + item + "'");
			}
        	
//        	int tamanhoLinha = 0;
//        	if (tipoArquivo == TipoArquivo.CNAB240)
//        		tamanhoLinha = 240;
//        	else if (tipoArquivo == TipoArquivo.CNAB400)
//        		tamanhoLinha = 400;
//        	
//        	// Normaliza espacos a direito na linha 
//        	linha = String.format("%-" + tamanhoLinha + "s", linha);
        	
	   	} catch (Exception e) {
	           throw new CobrancaExcecao(e);
	   	}
        	
		return linha;
	}
	
    private static boolean isString(String name) {
        return name.equals("java.lang.String");
    }

    private static boolean isChar(String name) {
        return name.equals("java.lang.Character") || name.equals("char");
    }

    private static boolean isByte(String name) {
        return name.equals("java.lang.Byte") || name.equals("byte");
    }

    private static boolean isObject(String name) {
        return name.equals("java.lang.Object");
    }
    
    private static boolean isInteger(String name) {
        return name.equals("java.lang.Integer") || name.equals("int");
    }

    private static boolean isLong(String name) {
        return name.equals("java.lang.Long") || name.equals("long");
    }    

    private static boolean isDouble(String name) {
        return name.equals("java.lang.Double") || name.equals("double");
    }

    private static boolean isBoolean(String name) {
        return name.equals("java.lang.Boolean") || name.equals("boolean");
    }

    private static boolean isTipoRegistro(String name) {
        return name.indexOf("TipoRegistro") > -1;
    }

}
