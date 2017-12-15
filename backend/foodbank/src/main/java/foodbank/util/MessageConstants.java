package foodbank.util;

public final class MessageConstants {

	public static final String USER_ADD_SUCCESS = "User successfully added.";
	public static final String USER_UPDATE_SUCCESS = "User successfully updated.";
	public static final String USER_DELETE_SUCCESS = "User successfully deleted.";
	public static final String LOGIN_SUCCESS = "User successfully authenticated.";
	public static final String ITEM_OVERWRITE_SUCCESS = "Item successfully updated.";
	public static final String ITEM_UPDATE_SUCCESS = "Item quantity successfully updated.";
	public static final String REQUEST_CREATE_SUCCESS = "Request successfully added.";
	public static final String REQUEST_UPDATE_SUCCESS = "Request successfully updated.";
	public static final String REQUEST_DELETE_SUCCESS = "Request successfully deleted.";
	public static final String BATCH_REQUEST_CREATE_SUCCESS = "Requests successfully added.";
	public static final String BENEFICIARY_RETRIEVE_SUCCESS = "Beneficiary details successfully found.";
	public static final String BENEFICIARY_UPDATE_SUCCESS = "Beneficiary successfully updated.";
	public static final String BENEFICIARY_ADD_SUCCESS = "Beneficiary successfully added.";
	public static final String WINDOW_OPEN_SUCCESS = "Window successfully opened";
	public static final String WINDOW_CLOSE_SUCCESS = "Window successfully closed";
	public static final String CLOSING_DATE_UPDATE_SUCCESS = "Closing date successfully updated.";
	public static final String DECAY_RATE_UPDATE_SUCCESS = "Decay rate successfully updated.";
	public static final String MULTIPLIER_RATE_UPDATE_SUCCESS = "Multiplier rate successfully updated.";
	public static final String ADMIN_BATCH_UPDATE_SUCCESS = "All admin settings successfully updated.";
	public static final String ALLOCATION_GENERATE_SUCCESS = "Allocations successfully generated.";
	
	public static class ErrorMessages {
		
		public static final String USER_ALREADY_EXISTS = "This user already exists.";
		public static final String NO_SUCH_USER = "This user does not exist.";
		public static final String INVALID_CREDENTIALS = "Invalid login credentials.";
		public static final String NO_SUCH_ITEM = "The specified food item cannot be found.";
		public static final String NO_SUCH_REQUEST = "The specified request cannot be found.";
		public static final String NO_SUCH_BENEFICIARY = "The requested beneficiary does not exist.";
		public static final String BENEFICIARY_ALREADY_EXISTS = "This beneficiary already exists.";
		public static final String DATE_PARSE_ERROR = "There was an error when parsing the date.";
		
	}
	
}
