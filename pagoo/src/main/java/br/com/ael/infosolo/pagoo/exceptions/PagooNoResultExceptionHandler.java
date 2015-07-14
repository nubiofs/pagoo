package br.com.ael.infosolo.pagoo.exceptions;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * Exceotion handler do pagoo para servicos REST
 * @author David Faulstich (davidfdr@gmail.com)
 * @since 10/07/2015
 *
 */
@Provider
public class PagooNoResultExceptionHandler implements ExceptionMapper<javax.persistence.NoResultException> {
	
    @Inject
    private Logger log;

	@Override
	public Response toResponse(javax.persistence.NoResultException noResultException) {
		log.error("Erro do Pagoo! {}",noResultException.toString(),noResultException);
		PagooExceptionResponse r = new PagooExceptionResponse();
		r.setStatus("NOT FOUND");
		r.setMessage(noResultException.getMessage());
		r.setStacktrace(ExceptionUtils.getStackTrace(noResultException));
		Response.status(Response.Status.NOT_FOUND).entity(r).type(MediaType.APPLICATION_JSON).build();
        return Response.status(Response.Status.NOT_FOUND).entity(r).type(MediaType.APPLICATION_JSON).build();
	}
	

	public class PagooExceptionResponse {
		private String status;
		private String message;
		private String stacktrace;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getStacktrace() {
			return stacktrace;
		}
		public void setStacktrace(String stacktrace) {
			this.stacktrace = stacktrace;
		}
		
	}

}



