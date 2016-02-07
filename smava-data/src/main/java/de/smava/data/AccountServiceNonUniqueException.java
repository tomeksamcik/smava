package de.smava.data;

/**
 * @author Tomek Samcik
 *
 *         Exception thrown by the service layer indicating that the given
 *         entity already exists
 * 
 */
public class AccountServiceNonUniqueException extends AccountServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7855930008675640661L;

	public AccountServiceNonUniqueException(String msg) {
		super(msg);
	}

}
