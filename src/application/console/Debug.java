package application.console;

import java.io.IOException;

public class Debug {
	public static void displayDebug(){
		System.out.print("Azurpixel / Saber LLC Launcher console, send a full capture of this to report any bugs\n");
		System.out.print("java version: " + System.getProperty("java.version") + '\n');
		System.out.print("os name: " + System.getProperty("os.name") + '\n');
		System.out.print("os arch: " + System.getProperty("os.arch") + '\n');
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			System.out.print("installing openjfx" + '\n');
			try {
				Runtime.getRuntime().exec("apt-get -y install openjfx");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.print("error while installing openjfx\n");
			}
		}
	}
}