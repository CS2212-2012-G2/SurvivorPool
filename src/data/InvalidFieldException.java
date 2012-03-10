/**
 * 
 */
package data;

// TODO: Docs:

/**
 * Exception class to show when an invalid type is attempted to be used.
 *
 */
public class InvalidFieldException extends Exception {

	public InvalidFieldException(String reason) {
		super(reason);
	}

	private static final long serialVersionUID = 1L;

}
