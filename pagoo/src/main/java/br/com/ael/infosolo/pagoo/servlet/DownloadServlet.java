package br.com.ael.infosolo.pagoo.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import br.com.infosolo.cobranca.negocio.ejb.CobrancaBancariaNegocioLocal;

import com.lowagie.text.pdf.codec.Base64;

/**
 * Servlet para download de PDFs em Base64.
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 15/07/2015
 *
 */
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
	
	@EJB
	CobrancaBancariaNegocioLocal cobrancaBancariaNegocioLocal;
	
	@Inject
	private Logger logger;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	logger.info("Iniciando Download de Boleto!");
    	
    	String nossoNumero = request.getParameter("nossonumero");
    	String nossoNumero2 = request.getParameter("nossonumero2");
    	
		byte[] byteArray = cobrancaBancariaNegocioLocal.retornarBoletoFisico(nossoNumero,nossoNumero2);
		
        if (byteArray != null) {
            response.setContentType("data:application/pdf;base64");
            response.addHeader("Content-disposition", "attachment; filename=\"" + String.format("Bordero_%s.pdf", nossoNumero) + "\"");
            String pdfBase64 = Base64.encodeBytes(byteArray);
            
            try {
                ServletOutputStream os = response.getOutputStream();
                os.print(pdfBase64);
                os.flush();
                os.close();
            } catch (Exception e) {
            	logger.error("\nFailure : " + e.toString() + "\n",e);
            }
        }
        
    	
        
    }
}