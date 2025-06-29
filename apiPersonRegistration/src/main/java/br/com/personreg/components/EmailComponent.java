package br.com.personreg.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailComponent {

	/*
	 * Ler o valor da conta de email que será utilizada para enviar as mensagens
	 */
	@Value("${spring.mail.username}")
	private String userName;

	/*
	 * Injeção de dependência para usarmos a biblioteca do Spring Mail
	 */
	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Método para realizar o envio de emails
	 */
	public void sendMail(String to, String subject, String body)
			throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		helper.setFrom(userName); // remetente do email
		helper.setTo(to); // destinatário do email
		helper.setSubject(subject); // assunto do email
		helper.setText(body); // conteúdo do email

		// enviando o email
		javaMailSender.send(mimeMessage);
	}
}
