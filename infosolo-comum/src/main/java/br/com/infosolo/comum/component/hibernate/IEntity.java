package br.com.infosolo.comum.component.hibernate;

import java.io.Serializable;

/**
 * Interface support a todas as entidades.
 * 
 *
 */
public interface IEntity extends Serializable{

	public Serializable getId();
	
}