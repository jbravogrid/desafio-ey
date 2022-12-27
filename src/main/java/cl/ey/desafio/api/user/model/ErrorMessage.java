package cl.ey.desafio.api.user.model;

public class ErrorMessage {
	
	
	public ErrorMessage(String message) {
		super();
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}
	
}
