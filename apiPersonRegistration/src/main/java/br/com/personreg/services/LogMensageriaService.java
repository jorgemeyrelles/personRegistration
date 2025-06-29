package br.com.personreg.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.personreg.collections.LogMensageria;
import br.com.personreg.repositories.LogMensageriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogMensageriaService {

	private final LogMensageriaRepository logMensageriaRepository;

	/**
	 * Busca todos os registros da coleção log_mensageria.
	 * 
	 * @return Lista de LogMensageria
	 */
	public List<LogMensageria> buscarTodos() {
		return logMensageriaRepository.findAll();
	}
}
