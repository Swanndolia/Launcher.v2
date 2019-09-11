package Launcher.minecraft.util;

public class ConnectToServer {
	
	private static String server;
	private static String serverPort;
	private static Boolean connectEnable = false;
	
	public ConnectToServer(String host) {
		this(host, "25565");
	}
	
	public ConnectToServer(String host, String port) {
		server= host;
		serverPort = port;
		connectEnable = true;
	}
	
	public static Boolean isActived() {
		return connectEnable;
	}
	
	public static void setActived(Boolean actived) {
		connectEnable = actived;
	}

	public static String getServer() {
		return server;
	}

	public static void setServer(String host) {
		server = host;
	}

	public static String getServerPort() {
		return serverPort;
	}

	public static void setServerPort(String port) {
		serverPort = port;
	}

}
