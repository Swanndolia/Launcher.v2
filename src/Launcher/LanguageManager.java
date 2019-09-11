package Launcher;

import java.util.HashMap;
import java.util.Locale;

public class LanguageManager {
	public static final HashMap<String, String> ENGLISH = new HashMap<String, String>();

	public static final HashMap<String, String> FRENCH = new HashMap<String, String>();

	private static HashMap<String, String> currentLangSet = ENGLISH;

	static {
		ENGLISH.put("hi-ext", "OpenLauncherLib 3.0.4 by Adrien 'Litarvan' Navratil - External Launching System");
		ENGLISH.put("jre-custom", "Java Custom Enabled | by Alwyn974");
		ENGLISH.put("mineweb-enable", "Mineweb Enabled | by Alwyn974");
		ENGLISH.put("connect-server", "Automatic connection to the server | by Alwyn974");
		ENGLISH.put("skin-utils", "You are using SkinUtils | By Alwyn974");
		ENGLISH.put("skin-error", "Invalid url, image not found : ");
		ENGLISH.put("options", "Options");
		ENGLISH.put("ram", "RAM");
		ENGLISH.put("warn", "Warning");
		ENGLISH.put("splash-interrupted", "Splash wait time was interrupted !");
		ENGLISH.put("ex-caught", "Exception caught !");
		ENGLISH.put("report-error", "Unable to write the crash report !");
		ENGLISH.put("ram-empty", "Can't read ram : File is empty");
		ENGLISH.put("writing-crash", "Writing crash report to");
		ENGLISH.put("load-fail", "Can't load the given jar");
		ENGLISH.put("jar-notfound", "Can't find the given jar");
		ENGLISH.put("loading", "Loading file");
		ENGLISH.put("mc-check", "Checking Minecraft directory");
		ENGLISH.put("mc-int", "Creating internal launching profile for Minecraft");
		ENGLISH.put("mc-ext", "Creating external launching profile for Minecraft");
		ENGLISH.put("mc-cp", "Generating classpath");
		ENGLISH.put("log-err", "Error while writing the logs !");
		ENGLISH.put("log-end", "Error, logging ended suddenly");
		ENGLISH.put("launching", "Launching program. It is now");
		ENGLISH.put("init", "Initializing main class");
		ENGLISH.put("start", "Starting");
		ENGLISH.put("total", "Total time");
		ENGLISH.put("security", "Detected certificate information error, please delete META-INF in your JAR");
		ENGLISH.put("nat", "Loading the natives");
		ENGLISH.put("done", "Done");
		ENGLISH.put("ent", "Entire command");

		FRENCH.put("hi-ext", "OpenLauncherLib 3.0.4 par Adrien 'Litarvan' Navratil - Systeme de lancement externe");
		FRENCH.put("jre-custom", "Java Custom Activé | by Alwyn974");
		FRENCH.put("mineweb-enable", "Mineweb Activé | by Alwyn974");
		FRENCH.put("connect-server", "Connexion automatique au Serveur | by Alwyn974");
		FRENCH.put("skin-utils", "Vous utilisez SkinUtils | By Alwyn974");
		FRENCH.put("skin-error", "Url invalide, image introuvable : ");
		FRENCH.put("options", "Options");
		FRENCH.put("ram", "RAM");
		FRENCH.put("warn", "Attention");
		FRENCH.put("splash-interrupted", "Le temps d'attente du splash a été interrompu !");
		FRENCH.put("ex-caught", "Exception attrapee !");
		FRENCH.put("report-error", "Impossible d'écrire le crash report !");
		FRENCH.put("ram-empty", "Impossible de lire la RAM : Le fichier est vide");
		FRENCH.put("writing-crash", "Ecriture du crash report dans");
		FRENCH.put("load-fail", "Impossible de charger le jar");
		FRENCH.put("jar-notfound", "Impossible de trouver le jar");
		FRENCH.put("loading", "Chargement du fichier");
		FRENCH.put("mc-check", "Verification du dossier de Minecraft");
		FRENCH.put("mc-int", "Creation d'un profil de lancement interne pour Minecraft");
		FRENCH.put("mc-ext", "Creation d'un profil de lancement externe pour Minecraft");
		FRENCH.put("mc-cp", "Generation du classpath");
		FRENCH.put("log-err", "Erreur en ecrivant les logs !");
		FRENCH.put("log-end", "Erreur, le systeme de logs s'est brusquement arrete");
		FRENCH.put("launching", "Lancement du programme. Il est actuellement");
		FRENCH.put("init", "Initialization de la classe principale");
		FRENCH.put("start", "Lancement de");
		FRENCH.put("total", "Temps total");
		FRENCH.put("security", "Une erreur de certification a ete detectee, merci de supprimer le dossier META-INF de votre .jar");
		FRENCH.put("nat", "Chargement des natives");
		FRENCH.put("done", "Termine");
		FRENCH.put("ent", "Commande entiere");
	}

	public static String lang(String... keys) {
		String total = "";
		String text;

		for (String key : keys) {
			text = currentLangSet.get(key);
			if (text == null)
				text = ENGLISH.get(key);

			total += (text == null ? key : text) + " ";
		}

		return total;
	}

	public static void setLang(HashMap<String, String> langSet) {
		currentLangSet = langSet;
	}

	public static HashMap<String, String> getCurrentLangSet() {
		return currentLangSet;
	}

	static {
		if (Locale.getDefault().getLanguage().toLowerCase().startsWith("fr")) {
			setLang(FRENCH);
		}
	}
}
