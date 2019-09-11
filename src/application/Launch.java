package application;

import Launcher.LaunchException;
import Launcher.external.BeforeLaunchingEvent;
import Launcher.external.ExternalLaunchProfile;
import Launcher.external.ExternalLauncher;
import Launcher.minecraft.GameFolder;
import Launcher.minecraft.MinecraftLauncher;
import Launcher.minecraft.util.ConnectToServer;	

public class Launch {
	public static void launch() throws LaunchException, InterruptedException 
	{


		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(Controller.infos, new GameFolder("assets", Controller.tweaks.get("libs"), Controller.tweaks.get("natives"), Controller.tweaks.get("bin")), Login.getAuthInfos());
		ExternalLauncher launcher = new ExternalLauncher(profile, new BeforeLaunchingEvent() {
			@Override
			public void onLaunching(ProcessBuilder processBuilder) {
				String javaPath = Controller.tweaks.get("java-path", "");
				if (javaPath != null && !javaPath.equals(""))
					processBuilder.command().set(0, javaPath);
			}
		});

		int exitCode = launcher.launch().waitFor();
		System.out.println("\nMinecraft finished with exit code " + exitCode);
	}

}
