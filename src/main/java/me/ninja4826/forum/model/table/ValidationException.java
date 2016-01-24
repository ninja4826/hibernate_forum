package me.ninja4826.forum.model.table;

public class ValidationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4815111808302006582L;
	
	public ValidationException() { super(); }
	public ValidationException(String message) { super(message); }
	public ValidationException(String message, Throwable cause) { super(message, cause); }
	public ValidationException(Throwable cause) { super(cause); }
}
