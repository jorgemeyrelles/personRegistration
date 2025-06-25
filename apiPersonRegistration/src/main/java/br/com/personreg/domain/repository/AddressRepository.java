package br.com.personreg.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personreg.domain.entity.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
