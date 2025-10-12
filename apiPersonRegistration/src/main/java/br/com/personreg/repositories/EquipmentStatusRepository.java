package br.com.personreg.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.personreg.entities.EquipmentStatus;

@Repository
public interface EquipmentStatusRepository extends JpaRepository<EquipmentStatus, UUID> {

	EquipmentStatus findByName(String nome);
}
