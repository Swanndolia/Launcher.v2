package Launcher;

@SuppressWarnings("serial")
public class LaunchException extends Exception {

	public LaunchException(String message) {
		super(message);
	}

	public LaunchException(String message, Throwable cause) {
		super(message, cause);
	}
}
