package utils;

public class ErrorMan {
	
	public static final int POST_NOT_FOUND = 0;
	
	public static final String ERROR_PARAM = "err=";
	
	public static String getErrorText(int errorCode) {
		
		String err = "Some error occurred";
		
		if(errorCode == POST_NOT_FOUND)
			err = "Post not found";
		
		return err + " :(";	
		
	}

}
