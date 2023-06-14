package com.api.rest.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

/**
 * Usuário que implementa UserDetails (interface que vai se conectar com o núcleo do Spring Security).
 */
@Entity
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(unique = true)
	private String login;
	
	private String senha;
	
	private String nome;
	
	@OneToMany(
		targetEntity=Telefone.class, // Entidade que representa
		mappedBy="usuario", // Mapeamento
		cascade=CascadeType.ALL, // Apagar um usuário implica em remover os respectivos telefones associados
		fetch=FetchType.LAZY // Carrega somente se tiver telefones para o usuário
	)
	private List<Telefone> telefones = new ArrayList<Telefone>();
	
	// Papéis de acesso
	// Cria uma tabela pivô referenciando usuário com role
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "usuarios_role", 
		uniqueConstraints = @UniqueConstraint (
			columnNames = {"usuario_id", "role_id"}, 
			name = "unique_role_user"
		),
		joinColumns = @JoinColumn(
			name = "usuario_id", 
			referencedColumnName = "id", 
			table = "usuario",
			unique = false, // Um usuário pode ter mais de uma role
			foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)
		),
		inverseJoinColumns = @JoinColumn(
			name = "role_id",
			referencedColumnName = "id",
			table = "role",
			updatable = false, // Evita ficar dando update acidental nos acessos (roles)
			unique = false, // Um usuário pode ter mais de uma role
			foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)
		)	
	)
	private List<Role> roles; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
	
	/**
	 * Acessos do usuário (roles)
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return this.senha;
	}
	
	@JsonIgnore
	@Override
	public String getUsername() {
		return this.login;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
