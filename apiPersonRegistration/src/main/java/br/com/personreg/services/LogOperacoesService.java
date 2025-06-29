package br.com.personreg.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.personreg.collections.LogOperações;
import br.com.personreg.repositories.LogOperacoesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogOperacoesService {

	private final LogOperacoesRepository logOperacoesRepository;

	public List<LogOperações> buscarTodos() {
		return logOperacoesRepository.findAll();
	}
}
