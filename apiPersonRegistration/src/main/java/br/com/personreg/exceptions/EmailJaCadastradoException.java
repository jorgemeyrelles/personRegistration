package br.com.personreg.exceptions;

public class EmailJaCadastradoException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "O email informado já está cadastrado. Tente outro.";
	}
}
