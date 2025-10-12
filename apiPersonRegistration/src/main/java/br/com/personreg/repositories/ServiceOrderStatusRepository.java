package br.com.personreg.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.personreg.entities.ServiceOrderStatus;

@Repository
public interface ServiceOrderStatusRepository extends JpaRepository<ServiceOrderStatus, UUID> {

	ServiceOrderStatus findByName(String name);
}
