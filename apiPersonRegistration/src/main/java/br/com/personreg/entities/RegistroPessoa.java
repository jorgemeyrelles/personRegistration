package br.com.personreg.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_registro_pessoa", uniqueConstraints = {
		@UniqueConstraint(columnNames = "cpf") // Garante unicidade do CPF
})
public class RegistroPessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(nullable = false, length = 20)
	private String telefone;

	@Column(nullable = false, length = 14, unique = true)
	private String cpf;

	// Endereço
	@Column(nullable = false, length = 10)
	private String numero;

	@Column(length = 50)
	private String complemento;

	@Column(nullable = false, length = 9)
	private String cep;

	@Column(nullable = false, length = 60)
	private String bairro;

	@Column(name = "municipio", nullable = false, length = 60)
	private String nomeMunicipio;

	@Column(name = "estado", nullable = false, length = 60)
	private String nomeEstado;
	
	@Column(name = "lat", nullable = false, length = 60)
	private String latitude;
	
	@Column(name = "longitude", nullable = false, length = 60)
	private String longitude;

	// Usuário que realizou o cadastro
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
}
