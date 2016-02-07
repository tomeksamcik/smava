package de.smava.data;

/**
 * @author Tomek Samcik
 *
 * Exception thrown by the service layer indicating that
 * entity with the given id does not exist
 */
public class AccountServiceEntityNotFoundException extends AccountServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7986810722901951408L;

	public AccountServiceEntityNotFoundException(String msg) {
		super(msg);
	}
	
}
