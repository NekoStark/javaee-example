package it.unifi.ing.stlab.swa.bean.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;

/**
 * http://docs.jboss.org/weld/reference/latest/en-US/html/injection.html#_the_literal_injectionpoint_literal_object
 */
public class HttpParams {

	@Produces @HttpParam("")
	public String getParamValue(InjectionPoint ip) {
		ServletRequest request = (ServletRequest)FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getRequest();
		
		return request.getParameter( ip.getAnnotated().getAnnotation(HttpParam.class).value() );
	}
}