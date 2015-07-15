package br.com.infosolo.cobranca.negocio.ejb;

import java.util.Date;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.comum.util.Logger;



/**
 * Session Bean responsável pelo envio de email.
 */
@Stateless
public class EmailNegocio implements EmailNegocioLocal {
	private static final Logger logger = new Logger(EmailNegocio.class);
	
    /**
     * Default constructor. 
     */
    public EmailNegocio() {
        
    }
    
    /*
     * (non-Javadoc)
     * @see br.com.infosolo.cobranca.negocio.ejb.EmailNegocioLocal#enviarMensagemEmail(java.lang.String[], java.lang.String, java.lang.String)
     */
    public boolean enviarMensagemEmail(String[] listaEmail, String assunto, String texto,
    		byte[] boletoFisico, byte[] anexoRelatorio, byte[] anexoPlanilha) {
    	boolean retorno = false;
    	
        try {
			Context initCtx = new InitialContext();
			
			logger.info("Iniciando sessao de email...");
			Session mailSession = null;
			
			try {
				mailSession = (javax.mail.Session)initCtx.lookup("java:/Mail");
			} catch (javax.naming.NamingException e) {
				e.printStackTrace();
			}

			InternetAddress[] destinatarios = new InternetAddress[listaEmail.length];
			logger.info("Adicionando destinatarios...");
			
			for (int i = 0; i < destinatarios.length; i++) {
				destinatarios[i] = new InternetAddress(listaEmail[i]);
				logger.info("-> " + listaEmail[i]);
			}
			
			logger.info("Email sera enviado para " + destinatarios.length +" destinatarios...");

			MimeMessage mensagem = new MimeMessage(mailSession);   
			mensagem.setSubject(assunto);
			mensagem.setSentDate(new Date());
			mensagem.setRecipients(Message.RecipientType.TO, destinatarios);   

			if (boletoFisico == null) {
				mensagem.setContent(texto, "text/plain");
				mensagem.setText(texto, "iso-8859-1");
			}
			else {
			    MimeBodyPart messageBodyPart = new MimeBodyPart();
			    messageBodyPart.setText(texto, "iso-8859-1");
	
	  	        Multipart multipart = new MimeMultipart();
			    multipart.addBodyPart(messageBodyPart);
				
			    if (anexoRelatorio != null) {
					logger.info("Adicionando Relatorio a mensagem...");
				    messageBodyPart = new MimeBodyPart();
				    //ByteArrayOutputStream os = new ByteArrayOutputStream ();
				    messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(anexoRelatorio, "aplication/pdf")));   
				    messageBodyPart.setFileName("Relatorio.pdf");  
				    multipart.addBodyPart(messageBodyPart);
			    }
			    
			    if (anexoPlanilha != null) {
					logger.info("Adicionando Planilha a mensagem...");
				    messageBodyPart = new MimeBodyPart();
				    messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(anexoPlanilha, "text/plain")));   
				    messageBodyPart.setFileName("Relatorio.csv");  
				    multipart.addBodyPart(messageBodyPart);
			    }
			    
				logger.info("Adicionando Boleto a mensagem...");
			    messageBodyPart = new MimeBodyPart();
			    //ByteArrayOutputStream os = new ByteArrayOutputStream ();
			    messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(boletoFisico, "aplication/pdf")));   
			    messageBodyPart.setFileName("Boleto.pdf");  
			     
			    multipart.addBodyPart(messageBodyPart);
			    
			    mensagem.setContent(multipart);
			}

			logger.info("Enviando email para os destinatarios...");

			Transport.send(mensagem);
			
			logger.info("Email enviado!");
			retorno = true;
			
		} catch (NoSuchProviderException nspe) {
			nspe.printStackTrace();
			logger.error("Erro enviando email: " + nspe.toString(),nspe);
			throw new CobrancaExcecao(nspe);
			
		} catch (NamingException ne) {
			ne.printStackTrace();
			logger.error("Erro enviando email: " + ne.toString(),ne);
			throw new CobrancaExcecao(ne);
			
		} catch (MessagingException me) {
			me.printStackTrace();
			logger.error("Erro enviando email: " + me.toString(),me);
			throw new CobrancaExcecao(me);
		}   
	    return retorno;
    }  
}
