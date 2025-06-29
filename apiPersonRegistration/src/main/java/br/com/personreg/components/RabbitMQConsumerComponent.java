package br.com.personreg.components;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.personreg.collections.LogMensageria;
import br.com.personreg.dtos.MensagemUsuarioResponse;
import br.com.personreg.repositories.LogMensageriaRepository;

@Component
public class RabbitMQConsumerComponent {

	@Autowired // injeção de dependência
	private EmailComponent emailComponent;

	@Autowired // injeção de dependência
	private LogMensageriaRepository logMensageriaRepository;

	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * Método para ler e processar cada mensagem contida na fila do servidor de
	 * mensageria (RabbitMQ)
	 */
	@RabbitListener(queues = { "mensagens-usuarios" })
	public void proccess(@Payload String message) throws Exception {

		LogMensageria logMensageria = new LogMensageria();
		logMensageria.setId(UUID.randomUUID());
		logMensageria.setDataHora(new Date());
		logMensageria.setOperacao("PROCESSAMENTO DE MENSAGEM DA FILA");

		try {

			// deserializar os dados que são lidos da fila (JSON -> Classe Java)
			MensagemUsuarioResponse mensagem = objectMapper.readValue(message,
					MensagemUsuarioResponse.class);

			// enviando a mensagem por email
			emailComponent.sendMail(mensagem.getEmailDestinatario(),
					mensagem.getAssunto(), mensagem.getTexto());

			logMensageria.setStatus("SUCESSO");
			logMensageria.setDescricao(message);
		} catch (Exception e) {
			logMensageria.setStatus("ERRO");
			logMensageria.setDescricao(e.getMessage());
		} finally {
			logMensageriaRepository.save(logMensageria);
		}
	}
}