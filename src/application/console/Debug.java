package application.console;

public class Debug {
	public static void displayDebug(){
		System.out.println("Azurpixel / Saber LLC Launcher console, send a full capture of this to report any bugs");
		System.out.println("java version: " + System.getProperty("java.version"));
		System.out.println("os name: " + System.getProperty("os.name"));
		System.out.println("os arch: " + System.getProperty("os.arch"));
	}
}