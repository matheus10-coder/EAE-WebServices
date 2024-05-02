package net.abcbs.eae.exception;

public class RPAMDMDataServiceException extends RuntimeException {
	private static final long serialVersionUID = -4314760119084400312L;
	private final String message;
	
	public RPAMDMDataServiceException() {
		this.message = null;
	}
	
	public RPAMDMDataServiceException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
