package br.com.personreg.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.personreg.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, UUID> {

	/*
	 * Consulta para obter 1 Perfil através do nome
	 */
	@Query("SELECT p FROM Perfil p WHERE p.nome = :nome")
	Perfil findByNome(@Param("nome") String nome);

	/*
	 * Consulta para buscar todos os perfis
	 */
	@Query("SELECT p FROM Perfil p")
	List<Perfil> findAllPerfis();
}
