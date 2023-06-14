package com.api.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.api.rest.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe gerenciador de Token
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * Construtor para o gerenciador de autenticação
	 * @param url 
	 * @param authenticationManager
	 */
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		// Autentica a URL
		super(new AntPathRequestMatcher(url));
		// Gerenciador de autenticação
		setAuthenticationManager(authenticationManager);
	}

	/**
	 * Retorna o usuário ao processar a autenticação.
	 * @return usuário, login, senha e acessos
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// Pega o token pra validar
		Usuario user = new ObjectMapper()
						.readValue(request.getInputStream(), Usuario.class);
		return getAuthenticationManager().
				authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	/**
	 * Ao realizar a autenticação bem sucedida, armazena o usuário autenticado
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		new JWTTokenAutenticacaoService()
			.addAuthentication(response, authResult.getName());
	}
	
}
