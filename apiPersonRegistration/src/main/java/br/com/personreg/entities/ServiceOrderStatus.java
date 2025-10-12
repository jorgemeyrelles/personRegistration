package br.com.personreg.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "service_order_status")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceOrderStatus {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "name", length = 50, nullable = false)
	private String name;
}
