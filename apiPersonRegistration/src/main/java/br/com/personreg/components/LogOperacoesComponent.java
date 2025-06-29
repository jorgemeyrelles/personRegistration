package br.com.personreg.components;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.personreg.collections.LogOperações;
import br.com.personreg.repositories.LogOperacoesRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogOperacoesComponent {
	private final LogOperacoesRepository logOperacoesRepository;

	public void log(String operacao, String status, String descricao) {
		LogOperações log = new LogOperações();
		log.setId(UUID.randomUUID());
		log.setDataHora(new Date());
		log.setOperacao(operacao);
		log.setStatus(status);
		log.setDescricao(descricao);
		logOperacoesRepository.save(log);
	}
}
