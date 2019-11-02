package application;

import Launcher.minecraft.AuthInfos;
import openauth.AuthPoints;
import openauth.AuthenticationException;
import openauth.Authenticator;
import openauth.model.AuthAgent;
import openauth.model.response.AuthResponse;
import openauth.model.response.RefreshResponse;

public class Login {
	
	public static AuthInfos authInfos;
	public static String name;
	
	public static void tryLogin(String user, String pass) throws AuthenticationException {
		if(pass.length() == 0) { 
			authInfos = new AuthInfos(user, "sry", "nope");
			name = user;
		}
		else {
			AuthResponse rep = new Authenticator("https://authserver.mojang.com/", AuthPoints.NORMAL_AUTH_POINTS).authenticate(AuthAgent.MINECRAFT, user, pass, Controller.tweaks.get("client-token", null));
			Controller.tweaks.set("client-token", rep.getClientToken());
			authInfos = new AuthInfos(rep.getSelectedProfile().getName(), rep.getAccessToken(), rep.getSelectedProfile().getId());
			name = rep.getSelectedProfile().getName();
			Controller.tweaks.set("access-token", rep.getAccessToken());
		}
	}
	public static void refresh() throws AuthenticationException {
		if (Controller.tweaks.get("access-token", "").equals("")) {
			authInfos = new AuthInfos(Controller.tweaks.get("username"), "sry", "nope");
			name = Controller.tweaks.get("username");
		}
		else {
			RefreshResponse rep = new Authenticator("https://authserver.mojang.com/", AuthPoints.NORMAL_AUTH_POINTS).refresh(Controller.tweaks.get("access-token"), Controller.tweaks.get("client-token"));
			authInfos = new AuthInfos(rep.getSelectedProfile().getName(), rep.getAccessToken(), rep.getSelectedProfile().getId());
			Controller.tweaks.set("access-token", rep.getAccessToken());
			name = rep.getSelectedProfile().getName();
		}
	}
}