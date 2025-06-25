package br.com.personreg.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personreg.domain.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {

	boolean existsByCpf(String cpf);

	Optional<Person> findByCpf(String cpf);
}
