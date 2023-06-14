package com.api.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filtro onde as requisições serão capturadas para autenticar.
 */
public class JWTApiAutenticacaoFilter extends GenericFilterBean {

	/**
	 * Estabelece a autenticação para a requisição.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = 
				new JWTTokenAutenticacaoService().getAuthentication((HttpServletRequest) request);
		// Coloca o processo de autenticação no Spring Security
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Contiua o processo
		chain.doFilter(request, response);
	}

}
