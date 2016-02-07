package de.smava.rest;

/**
 * @author Tomek Samcik
 *
 *         API Error envelope
 */
public class Error {

	/**
	 * Error response code
	 */
	private int code;

	/**
	 * Error message
	 */
	private String error;

	public Error(int code, String error) {
		this.code = code;
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public String getError() {
		return error;
	}

}
