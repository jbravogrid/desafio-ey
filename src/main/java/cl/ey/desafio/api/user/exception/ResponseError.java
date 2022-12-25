package cl.ey.desafio.api.user.exception;

public class ResponseError {
	
	private final String mensaje;

	public ResponseError(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	
		
}
