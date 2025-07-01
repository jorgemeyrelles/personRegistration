package br.com.personreg.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personreg.entities.RegistroPessoa;

public interface RegistroPessoaRepository
		extends JpaRepository<RegistroPessoa, UUID> {

	// Método para encontrar registros de pessoa por CPF
	Optional<RegistroPessoa> findByCpf(String cpf);

	// Método para verificar se um registro de pessoa existe pelo CPF
	boolean existsByCpf(String cpf);

	// Método para encontrar registros de pessoa por nome
	List<RegistroPessoa> findByNomeContainingIgnoreCase(String nome);

	// Método para buscar registros de pessoa por telefone
	Optional<RegistroPessoa> findByTelefone(String telefone);

}
