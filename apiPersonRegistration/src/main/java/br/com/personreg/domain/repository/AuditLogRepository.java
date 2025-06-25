package br.com.personreg.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.personreg.domain.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

}
