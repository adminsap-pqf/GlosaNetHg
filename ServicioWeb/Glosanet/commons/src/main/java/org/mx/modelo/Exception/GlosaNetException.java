package org.mx.modelo.Exception;

public class GlosaNetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3541360788612040100L;

	/**
	 * 
	 */
	public GlosaNetException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public GlosaNetException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GlosaNetException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public GlosaNetException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public GlosaNetException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
