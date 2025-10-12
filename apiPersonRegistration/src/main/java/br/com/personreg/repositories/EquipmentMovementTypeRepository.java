package br.com.personreg.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.personreg.entities.EquipmentMovementType;

@Repository
public interface EquipmentMovementTypeRepository extends JpaRepository<EquipmentMovementType, UUID> {

	EquipmentMovementType findByName(String name);
}
