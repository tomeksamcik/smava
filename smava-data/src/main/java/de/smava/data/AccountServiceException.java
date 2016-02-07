package de.smava.data;

/**
 * @author Tomek Samcik
 *
 * Exception thrown by the service layer indicating 
 * some general service layer exception
 */
public class AccountServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8342860881253912681L;

	public AccountServiceException(String msg) {
		super(msg);
	}
	
}
