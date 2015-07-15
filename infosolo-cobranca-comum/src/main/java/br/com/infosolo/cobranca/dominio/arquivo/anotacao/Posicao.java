package br.com.infosolo.cobranca.dominio.arquivo.anotacao;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) 
@Target(value={FIELD,METHOD})
public @interface Posicao {

	public String value();

}
