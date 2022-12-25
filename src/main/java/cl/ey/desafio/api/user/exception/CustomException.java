package cl.ey.desafio.api.user.exception;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final ErrorEnum cod;
	
	public CustomException(ErrorEnum cod, String msg) {
		super(msg);
		this.cod = cod;
	}

	public ErrorEnum getCod() {
		return cod;
	}
}
