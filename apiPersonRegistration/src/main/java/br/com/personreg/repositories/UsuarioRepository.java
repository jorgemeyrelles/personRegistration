package br.com.personreg.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.personreg.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	/*
	 * Consulta para onter 1 usuário através do email
	 */
	@Query("SELECT u FROM Usuario u JOIN u.perfil p WHERE u.email = :email")
	Usuario findByEmail(@Param("email") String email);

	/*
	 * Consulta para obter 1 usuário através do email e da senha
	 */
	@Query("SELECT u FROM Usuario u JOIN u.perfil p WHERE u.email = :email AND u.senha = :senha")
	Usuario findByEmailAndSenha(@Param("email") String email,
			@Param("senha") String senha);
}
