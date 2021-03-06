
package data;

/**
 * Exception class to show when an invalid type is attempted to be used.
 *
 * @author Kevin Brightwell
 */

public class InvalidFieldException extends RuntimeException {

	/**
	 * enum keys that will be returned depending on where the error exists.
	 */
	public enum Field {
		UNKNOWN_FIELD,
		// User:
		USER_ID, USER_ID_DUP, USER_FIRST, USER_LAST, USER_WEEKLY_PICK, USER_ULT_PICK, USER_ULT_PTS,
		// Contestants:
		CONT_ID, CONT_ID_DUP, CONT_FIRST, CONT_LAST, CONT_TRIBE, CONT_DATE
	}

	private Field problemField;

	private boolean handled = false;

	public InvalidFieldException(Field field, String reason) {
		this(reason);

		problemField = field;
	}

	public InvalidFieldException(String reason) {
		super(reason);

		problemField = Field.UNKNOWN_FIELD;
	}

	public Field getField() {
		return problemField;
	}

	public boolean isHandled() {
		return handled;
	}

	public void handle() {
		handled = true;
	}

	private static final long serialVersionUID = 1L;

}
