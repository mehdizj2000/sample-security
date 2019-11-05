package au.com.jaycar.exception;

public class TokenVerificationException extends RuntimeException{

	private static final long serialVersionUID = -6821373000021018947L;

	public TokenVerificationException() {
		super("Token is invalid");
	}
	

}
