package br.com.personreg.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObterDadosServiceOrderStatusResponse {
	private UUID id;
	private String name;
}
