package com.api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.api.rest.service.UserDetailsServiceImpl;

/**
 * Classe responsável por centralizar a segurança e autenticação.
 * Mapeia URL, endereços, autoriza ou bloqueia acessos a URLs.
 * Responsável por interceptar (servir de middleware) para acessar as APIs.
 */
@Configuration // Realiza as configurações de segurança
@EnableWebSecurity // Habilitar os recursos de segurança da classe
public class WebConfigSecurity extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	/**
	 * Validação - URL permissões e bloqueios.
	 * Tem proteção contra usuários que não estão validados por token.
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*Ativando a proteção contra usuário que não estão validados por TOKEN*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		/*Ativando a permissão para acesso a página incial do sistema EX: sistema.com.br/index*/
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		/*URL de Logout - Redireciona após o user deslogar do sistema*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*Maperia URL de Logout e insvalida o usuário*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*Filtra requisições de login para autenticação*/
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
				UsernamePasswordAuthenticationFilter.class)
		
		/*Filtra demais requisições para verificar a presenção do TOKEN JWT no HEADER HTTP*/
		.addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Consulta o usuário no banco de dados com codificação de senha BCrypt
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
