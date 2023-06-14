package com.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.rest.model.Usuario;
import com.api.rest.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Consulta um usuário no banco de dados e retorna os dados do usuário com base no login.
	 * Retorna um usuário (objeto User do Spring Framework) que será responsável pela autorização de acordo com os roles.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findUserByLogin(username);
		if (usuario == null) { // Se usuário não encontrado.
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
		}
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}
	
}
