package br.com.ael.infosolo.pagoo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe de utilizades do pagoo.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 10/07/2015
 *
 */
public class PagooUtil {
	
	/**
	 * Utiliza Object Mapper do Jackson para transformar objeto pojo JAVA
	 * em string JSON.
	 * @param obj
	 * @return String json
	 */
	public static String toJsonString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
		}
		return json;
	}
	
	/**
	 * Limpar strings formataçoes e etc.
	 * @param suja
	 * @return
	 */
	public static String limparString(String suja){
        String limpa = suja;  
        limpa = limpa.replaceAll("[-+=*&amp;%$#@!_]", "");
        limpa = limpa.replaceAll("['\"]", "");
        limpa = limpa.replaceAll("[<>()\\{\\}]", "");
        limpa = limpa.replaceAll("['\\\\.,()|/]", "");
        limpa = limpa.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", " ");
        
        return limpa;
	}

}
