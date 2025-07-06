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
	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

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

		helper.setFrom(username);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);

		// enviando o email
		javaMailSender.send(mimeMessage);
	}

	public void sendHtmlMail(String to, String subject, String htmlBody)
			throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,
				"UTF-8");

		helper.setFrom(username);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);

		javaMailSender.send(mimeMessage);
	}
}
