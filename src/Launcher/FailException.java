package Launcher;

@SuppressWarnings("serial")
public class FailException extends RuntimeException {
	public FailException(String message) {
		super("Ups ! Looks like you failed : " + message);
	}

	public FailException(String message, Throwable cause) {
		super("Ups ! Looks like you failed : " + message, cause);
	}
}
