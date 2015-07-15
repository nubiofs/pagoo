package br.com.infosolo.comum.util;


import org.slf4j.LoggerFactory;

/**
 * Esta classe controla os logs do Sistema
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 */
public class Logger {

    private org.slf4j.Logger logger = null; 
    
    /**
     * Cria a instancia da classe de log do sistema
     *
     * @param name O nome da classe relacionada no log
     */
    public Logger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }
    
    @SuppressWarnings({ "rawtypes" })
	public Logger(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }    
    
    /**
     * Cria a instancia da classe de log do sistema
     *
     * @param name O nome da classe relacionada no log
     */
    public void createInstance(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }
    
    /**
     * Adiciona uma mensagem de nivel INFO no log do sistema
     *
     * @param msg A mensagem a ser enviada
     * @return
     */
    @SuppressWarnings("deprecation")
	public void info(String msg) {
        logger.info(msg);
    }
    
    /**
     * Adiciona uma mensagem de nivel SEVERE no log do sistema
     *
     * @param msg A mensagem de erro associada com a execao
     * @return
     */
    @SuppressWarnings("deprecation")
	public void error(String msg) {
        logger.error(msg);
    }
    
    /**
     * Adiciona uma mensagem de nivel SEVERE no log do sistema
     * 
     * @param msg A mensagem de erro associada com a execao
     * @param cause A causa raiz da execao
     */
    @SuppressWarnings("deprecation")
	public void error(String msg, Throwable cause) {
        logger.error(msg, cause);
    }
    
    /**
     * Método adicionado para compatibilidade.
     * @param msg
     */
	public void debug(String msg) {
        logger.error(msg);
    }
}