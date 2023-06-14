package com.api.rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.api.rest.model.Usuario;

/**
 * CrudRepository: fornece todos os métodos CRUD do banco de dados
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	/**
	 * Encontra o usuário apenas pelo login, para algumas validações.
	 * Implementa uma query no banco de dados
	 * @param login Login de acesso
	 * @return objeto Usuário
	 */
	@Query(
		"select u from Usuario u " +
		"where u.login = ?1"
	)
	Usuario findUserByLogin(String login);
}
