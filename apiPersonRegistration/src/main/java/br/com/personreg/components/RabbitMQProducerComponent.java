package br.com.personreg.components;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.personreg.collections.LogMensageria;
import br.com.personreg.dtos.MensagemUsuarioResponse;
import br.com.personreg.repositories.LogMensageriaRepository;

@Component
public class RabbitMQProducerComponent {

	/*
	 * Permitir acessar o servidor do RabbitMQ
	 */
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/*
	 * Permitir acessar a fila do servidor
	 */
	@Autowired
	private Queue queue;

	/*
	 * Permitir serializar os dados que serão gravados na fila enviar estes
	 * dados para a fila em formato JSON
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * Permitir o acesso ao repositório para gravação de dados no banco do
	 * MongoDB
	 */
	@Autowired
	private LogMensageriaRepository logMensageriaRepository;

	/*
	 * Método para gravar uma mensagem de usuário na fila
	 */
	public void send(MensagemUsuarioResponse mensagem) throws Exception {

		LogMensageria logMensageria = new LogMensageria();
		logMensageria.setId(UUID.randomUUID());
		logMensageria.setDataHora(new Date());
		logMensageria.setOperacao("ENVIO DE MENSAGEM PARA A FILA");

		try {

			// serializar os dados da mensagem em formato JSON
			String json = objectMapper.writeValueAsString(mensagem);

			// enviando para o servidor de mensageria (fila)
			rabbitTemplate.convertAndSend(queue.getName(), json);

			logMensageria.setStatus("SUCESSO");
			logMensageria.setDescricao(json);
		} catch (Exception e) {

			logMensageria.setStatus("ERRO");
			logMensageria.setDescricao(e.getMessage());
		} finally {
			logMensageriaRepository.save(logMensageria);
		}
	}
}
