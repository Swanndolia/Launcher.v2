package application;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import re.alwyn974.openlauncherlib.minecraft.AuthInfos;

public class Auth {

	public static final Authenticator AP_AUTH = new Authenticator("https://authserver.mojang.com/", AuthPoints.NORMAL_AUTH_POINTS);
	
	public static AuthInfos authInfos;
	private static String name;
	
	public static void tryAuth(String user, String pass) throws AuthenticationException {
		if(pass.length() == 0) {
			authInfos = new AuthInfos(user, "sry", "nope");
			name = user;
		}
		else {
			AuthResponse rep = AP_AUTH.authenticate(AuthAgent.MINECRAFT, user, pass, "");
			authInfos = new AuthInfos(rep.getSelectedProfile().getName(), rep.getAccessToken(), rep.getSelectedProfile().getId());
			name = rep.getSelectedProfile().getName();
		}
	}

	public static String getName() {
		return name;
	}
}