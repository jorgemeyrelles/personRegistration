package br.com.personreg.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "audit_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String endpoint;

	@Column(nullable = false)
	private String method;

	@Column(name = "request_data", columnDefinition = "TEXT")
	private String requestData;

	@Column(name = "response_data", columnDefinition = "TEXT")
	private String responseData;

	@Column(name = "status_code")
	private Integer statusCode;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	@Column(name = "ip_address")
	private String ipAddress;
}
