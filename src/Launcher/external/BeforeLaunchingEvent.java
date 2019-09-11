package Launcher.external;

public interface BeforeLaunchingEvent {

	void onLaunching(ProcessBuilder builder);
}
