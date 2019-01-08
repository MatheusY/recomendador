package br.com.recomendador.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.recomendador.exception.SystemException;

public abstract  class AbstractController implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920627634033726418L;

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	protected HttpSession getHttpSession(final boolean create) {
		return (HttpSession) getFacesContext().getExternalContext().getSession(create);
	}
	
	protected Object getSessionAttribute(final String attributeName) {
		return this.getHttpSession(true).getAttribute(attributeName);
	}
	
	protected void setSessionAttribute(final String attributeName, final Serializable attributeValue) {
		this.getHttpSession(true).setAttribute(attributeName, attributeValue);
	}
	
	public void renderizarTela() {
		String contextParameter = getParameterFromExternalContext("context") + ".xhtml";
		
		try {
			this.redirect(contextParameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void redirect(String pagina) throws SystemException {
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() + pagina);
		} catch (IOException e) {
			throw new SystemException("Erro ao realizar o redirect", e);
		}
		
	}

	private String getParameterFromExternalContext(String contexto) {
		ExternalContext ec = getExternalContext();
		Map<String, String> params = ec.getRequestParameterMap();
		String parameter = "/" +  params.get(contexto);
		return parameter;
	}

	private ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}
}
