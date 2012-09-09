package cs5031.catalogplayer.catalog.exist;

/**
 * Exception class for internal logical errors
 * @author <110017972>
 *
 */
public class ExistException extends RuntimeException {
	/*
	 * This exception is RuntimeException because the interfaces don't have exceptions in their signatures
	 */
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new exception with specific message
	 * @param message Message of the exception
	 */
	public ExistException(String message) {
		super(message);
	}
	
	/**
	 * Wrap an exception and add a specific message
	 * @param message Message of the exception
	 * @param inner Exception that is wrapped
	 */
	public ExistException(String message, Exception inner) {
		super(message, inner);
	}
	
	/**
	 * Wrap an exception
	 * @param inner Exception to wrap
	 */
	public ExistException(Exception inner) {
		super(inner.getMessage(), inner);
	}
}