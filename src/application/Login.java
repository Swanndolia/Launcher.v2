package application;

import Launcher.minecraft.AuthInfos;
import openauth.AuthPoints;
import openauth.AuthenticationException;
import openauth.Authenticator;
import openauth.model.AuthAgent;
import openauth.model.response.AuthResponse;

public class Login {
	
	public static AuthInfos authInfos;
	private static String name;
	
	public static void tryLogin(String user, String pass) throws AuthenticationException {
		if(user.length() == 0 && pass.equals("Disconnect me")) {
			authInfos = new AuthInfos("", "", "");
			name = user;
		}
		else if(pass.length() == 0) {
			authInfos = new AuthInfos(user, "sry", "nope");
			name = user;
		}
		else {
			AuthResponse rep = new Authenticator("https://authserver.mojang.com/", AuthPoints.NORMAL_AUTH_POINTS).authenticate(AuthAgent.MINECRAFT, user, pass, "");
			authInfos = new AuthInfos(rep.getSelectedProfile().getName(), rep.getAccessToken(), rep.getSelectedProfile().getId());
			name = rep.getSelectedProfile().getName();
		}
	}
	
	public static String getName() {
		return name;
	}

	public static AuthInfos getAuthInfos() {
		return authInfos;
	}
}