package application;

import static application.Controller.tweaks;

import Launcher.LaunchException;
import Launcher.external.BeforeLaunchingEvent;
import Launcher.external.ExternalLaunchProfile;
import Launcher.external.ExternalLauncher;
import Launcher.minecraft.GameFolder;
import Launcher.minecraft.MinecraftLauncher;
import javafx.application.Platform;	

public class Launch {

	public static String natives;
	public static void launch() throws LaunchException, InterruptedException 
	{
		String memory = "-Xmx" + tweaks.get("memory") + "m";
		
		if (tweaks.get("version").equals("1.14"))
    		natives = "bin/natives/1.14";
		else if (tweaks.get("version").equals("1.7"))
    		natives = "bin/natives/1.7";
		else if (tweaks.get("version").equals("1.13"))
    		natives = "bin/natives/1.13";
		else
    		natives = "bin/natives/1.8-1.12";
		
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(Controller.infos, new GameFolder("assets", "libs/" + tweaks.get("version"), natives, "bin/" + tweaks.get("version") +".jar"), Login.authInfos);
		profile.getVmArgs().add(memory);
		ExternalLauncher launcher = new ExternalLauncher(profile, new BeforeLaunchingEvent() {
			@Override
			public void onLaunching(ProcessBuilder processBuilder) {
				String javaPath = tweaks.get("java-path", "");
				if (javaPath != null && !javaPath.equals(""))
					processBuilder.command().set(0, javaPath);
			}
		});
		Platform.runLater(()-> 	Main.stage.hide());
		int exitCode = launcher.launch().waitFor();
		Platform.runLater(()-> 	Main.stage.show());
		System.out.println("\nMinecraft finished with exit code " + exitCode);
	}

}
