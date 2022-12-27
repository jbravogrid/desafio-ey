package cl.ey.desafio.api.user.exception;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1046766345855859016L;
	
	private ErrorEnum codigo;

	public CustomException(ErrorEnum codigo, String msg) {
		super(msg);
		this.codigo = codigo;
	}

	public ErrorEnum getCodigo() {
		return codigo;
	}

	public void setCodigo(ErrorEnum codigo) {
		this.codigo = codigo;
	}
	

}
