package Launcher.external;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Launcher.JavaUtil;
import Launcher.LaunchException;
import Launcher.util.LogUtil;
import Launcher.util.ProcessLogManager;

public class ExternalLauncher {

	private BeforeLaunchingEvent launchingEvent;

	private ExternalLaunchProfile profile;

	private boolean logsEnabled = true;
	
	public ExternalLauncher(ExternalLaunchProfile profile) {
		this(profile, null);
	}

	public ExternalLauncher(ExternalLaunchProfile profile, BeforeLaunchingEvent launchingEvent) {
		this.profile = profile;
		this.launchingEvent = launchingEvent;
	}

	public boolean isLogsEnabled() {
		return logsEnabled;
	}

	public void setLogsEnabled(boolean logsEnabled) {
		this.logsEnabled = logsEnabled;
	}

	public Process launch() throws LaunchException {
		
		LogUtil.info("hi-ext");

		ProcessBuilder builder = new ProcessBuilder();
		ArrayList<String> commands = new ArrayList<String>();
		commands.add(JavaUtil.getJavaCommand());
		commands.addAll(Arrays.asList(JavaUtil.getSpecialArgs()));

		if (profile.getMacDockName() != null && System.getProperty("os.name").toLowerCase().contains("mac"))
			commands.add(JavaUtil.macDockName(profile.getMacDockName()));
		if (profile.getVmArgs() != null)
			commands.addAll(profile.getVmArgs());

		commands.add("-cp");
		commands.add(profile.getClassPath());
		commands.add(profile.getMainClass());

		if (profile.getArgs() != null)
			commands.addAll(profile.getArgs());

		if (profile.getDirectory() != null)
			builder.directory(profile.getDirectory());

		if (profile.isRedirectErrorStream())
			builder.redirectErrorStream(true);

		if (launchingEvent != null)
			launchingEvent.onLaunching(builder);

		builder.command(commands);

		String entireCommand = "";
		for (String command : commands)
			entireCommand += command + " ";

		LogUtil.info("ent", ":", entireCommand);
		LogUtil.info("start", profile.getMainClass());

		try {
			Process p = builder.start();

			if (logsEnabled) {
				ProcessLogManager manager = new ProcessLogManager(p.getInputStream());
				manager.start();
			}

			return p;
		} catch (IOException e) {
			throw new LaunchException("Cannot launch !", e);
		}
	}

	public BeforeLaunchingEvent getLaunchingEvent() {
		return launchingEvent;
	}


	public void setLaunchingEvent(BeforeLaunchingEvent launchingEvent) {
		this.launchingEvent = launchingEvent;
	}

	public ExternalLaunchProfile getProfile() {
		return profile;
	}

	public void setProfile(ExternalLaunchProfile profile) {
		this.profile = profile;
	}
}
