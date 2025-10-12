package br.com.personreg.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.personreg.entities.ConsumableMovementType;

@Repository
public interface ConsumableMovementTypeRepository extends JpaRepository<ConsumableMovementType, UUID> {

	ConsumableMovementType findByName(String name);
}
