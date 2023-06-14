package com.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.rest.ApplicationContextLoad;
import com.api.rest.model.Usuario;
import com.api.rest.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	// Validade do token (2 dias)
	private static final long EXPIRATION_TIME = 172800000;
	
	// Senha interna para segurança, usado na autenticação
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	// Prefixo padrão de token
	private static final String TOKEN_PREFIX = "Bearer";
	
	// Identificação do cabeçalho da requisição
	private static final String HEADER_STRING = "Authorization";
	
	/**
	 * Gera o token de autenticação e adiciona ao cabeçalho e resposta HTTP.
	 * @param response resposta da requisição
	 * @param username nome do usuário
	 * @throws Exception
	 */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		// Monta o Token
		String JWT = Jwts.builder() // Gerador de token
						.setSubject(username) // Usuário de acesso (login)
						.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME)) // Tempo de expiração do token
						.signWith(SignatureAlgorithm.HS512, SECRET).compact(); // Compactação e algoritmo HS512 (geração de senha)
		// Juntando o token gerado com o prefixo
		String token = TOKEN_PREFIX + " " + JWT;
		
		// Adiciona no cabeçalho HTTP
		response.addHeader(HEADER_STRING, token);
		
		// Escreve o token como resposta no corpo HTTP (JSON)
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
	}
	
	/**
	 * Método chamado quando for feita alguma requisição HTTP do cliente
	 * @param request requisição do navegador
	 * @return Retorna o usuário validado com o token ou caso não seja válido, retorna null. Retorna o usuário logado (padrão Spring, encapsulado no objeto UsernamePasswordAuthenticationToken)
	 */
	public Authentication getAuthentication(HttpServletRequest request) {
		// Token enviado no cabeçalho HTTP
		String token = request.getHeader(HEADER_STRING);
		if (token!=null) {
			// Faz a validação do token do usuário na requisição
			String user = Jwts.parser().setSigningKey(SECRET)
							.parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // Para retornar só a numeração
							.getBody().getSubject(); 
			if (user != null) {
				Usuario usuario = ApplicationContextLoad.getApplicationContext()
									.getBean(UsuarioRepository.class).findUserByLogin(user);
				
				return (usuario!=null) ? new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities()) : null;
			}
		}
		
		return null; // Não autorizado
	}
}
