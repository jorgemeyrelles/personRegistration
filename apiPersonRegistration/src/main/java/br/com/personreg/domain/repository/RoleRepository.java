package br.com.personreg.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personreg.domain.entity.Role;
import br.com.personreg.domain.entity.RoleName;

public interface RoleRepository extends JpaRepository<Role, UUID> {

	Optional<Role> findByName(RoleName name);
}
