package application;

import fr.theshark34.supdate.SUpdate;

public class Update {
	public static void update() throws Exception {
		SUpdate su = new SUpdate("https://azurpixel.net/launcher/" + Controller.tweaks.get("version") + "/", Controller.infos.getGameDir());
		su.getServerRequester().setRewriteEnabled(true);
		su.start();
	}
}
