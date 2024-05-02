package net.abcbs.eae.power;

public class PowerException extends RuntimeException {
	private static final long serialVersionUID = -7902414840456241608L;
	private final String message;

	public PowerException() {
		this.message = null;
	}
	
	public PowerException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
