package br.com.infosolo.cobranca.negocio.ejb;

import java.util.ArrayList;
import javax.ejb.Local;

import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;

@Local
public interface RetornoBancarioNegocioLocal {

	/**
	 * Processa os retornos bancarios.
	 */
	public ArrayList<BoletoEntidadeLite> processarRetornoBancario();
	
	/**
	 * Inicia o agendamento para processamento dos arquivos de retorno.
	 */
	public void iniciarAgendamento(long milisegundos);
	
	/**
	 * Cancela o agendamento.
	 */
	public void cancelarAgendamento();

}
