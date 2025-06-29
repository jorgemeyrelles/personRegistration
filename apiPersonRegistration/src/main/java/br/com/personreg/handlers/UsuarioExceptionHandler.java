package br.com.personreg.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.personreg.exceptions.AcessoNegadoException;
import br.com.personreg.exceptions.EmailJaCadastradoException;

@ControllerAdvice
public class UsuarioExceptionHandler {

	@ExceptionHandler(EmailJaCadastradoException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public List<String> errorHandler(EmailJaCadastradoException e) {

		List<String> errors = new ArrayList<String>();
		errors.add(e.getMessage());

		return errors;
	}

	@ExceptionHandler(AcessoNegadoException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public List<String> errorHandler(AcessoNegadoException e) {

		List<String> errors = new ArrayList<String>();
		errors.add(e.getMessage());

		return errors;
	}
}