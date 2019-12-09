package Launcher.minecraft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Launcher.util.Saver;
import application.Controller;

public class Presets {

	static File controlsF = new File(Controller.dir, "/controls.txt");
	static File mergedF = new File(Controller.dir, "/merged.txt");
	static File optionsF = new File(Controller.dir, "/options.txt");
	static File optionsof = new File(Controller.dir, "/optionsof.txt");
	static File presetsF = new File(Controller.dir,
			"/presets/" + Controller.tweaks.get("version") + '/' + Controller.tweaks.get("graphics") + "/options.txt");
	static File presetsofF = new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + '/'
			+ Controller.tweaks.get("graphics") + "/optionsof.txt");
	static Saver controls;

	public static void presetSet() throws IOException {
		if (!Controller.tweaks.get("graphics").equals("Custom"))
			deleteFiles();
		if (Controller.tweaks.get("version").equals("1.13") || Controller.tweaks.get("version").equals("1.14")) {
			controlsF = new File(Controller.dir, "/controls13.txt");
			if (!controlsF.exists())
				controlsFirstSet13();
		} else {
			controls = new Saver(controlsF);
			if (!controlsF.exists())
				controlsFirstSet();
		}
		writeFiles();
		convertToOptions();
	}

	public static void deleteFiles() {
		if (!Controller.tweaks.get("graphics").equals("Custom")) {
			optionsof.delete();
			optionsF.delete();
		}
		mergedF.delete();
		
	}

	private static void convertToOptions() throws IOException {
		BufferedReader merged = new BufferedReader(new FileReader(mergedF));
		BufferedWriter options = new BufferedWriter(new FileWriter(optionsF));
		String str;
		str = merged.readLine();

		while (str != null) {
			str = str.replaceAll("=", ":");
			options.write(str);
			options.write('\n');
			options.flush();
			str = merged.readLine();
		}
		options.close();
		merged.close();
	}

	public static void writeFiles() throws IOException {
		OutputStream out = new FileOutputStream(mergedF);
		OutputStream out2 = new FileOutputStream(optionsof);
		InputStream in2;
		InputStream in3;
		try {
			byte[] buf = new byte[8192];
			int len;
			int len2;
			int len3;
			InputStream in = new FileInputStream(controlsF);
			if (Controller.tweaks.get("graphics").equals("Custom"))
				in2 = null;
			else
				in2 = new FileInputStream(presetsF);
			if (Controller.tweaks.get("graphics").equals("Custom"))
				in3 = null;
			else
				in3 = new FileInputStream(presetsofF);
			try {
				while ((len = in.read(buf)) >= 0) {
					out.write(buf, 0, len);
				}
				while ((len2 = in2.read(buf)) >= 0) {
					out.write(buf, 0, len2);
				}
				while ((len3 = in3.read(buf)) >= 0) {
					out2.write(buf, 0, len3);
				}
			} finally {
				in.close();
				in2.close();
				in3.close();
			}
		} finally {
			out2.close();
			out.close();
		}
	}

	public static void presetSave() throws IOException {
		if (Controller.tweaks.get("version").equals("1.13") || Controller.tweaks.get("version").equals("1.14"))
			controlsF = new File(Controller.dir, "/controls13.txt");
		controls = new Saver(controlsF);
		controlsF.delete();
		convertToControls();
	}

	private static void convertToControls() throws IOException {
		BufferedReader options = new BufferedReader(new FileReader(optionsF));
		BufferedWriter controls = new BufferedWriter(new FileWriter(controlsF));
		String str;
		str = options.readLine();

		while (str != null) {
			if (str.startsWith("key") || str.startsWith("sound") || str.startsWith("chat") || str.startsWith("resource") || str.startsWith("gui") || str.contains("mouse") || str.startsWith("fov") || str.startsWith("max") || str.startsWith("lang")) {
				str = str.replaceAll(":", "=");
				controls.write(str);
				controls.write('\n');
				controls.flush();
			}
			str = options.readLine();
		}
		options.close();
		controls.close();
	}

	public static void controlsFirstSet() {
		controls.set("soundCategory_master", "0.6");
		controls.set("soundCategory_music", "0.3");
		controls.set("soundCategory_record", "1.0");
		controls.set("soundCategory_weather", "1.0");
		controls.set("soundCategory_block", "1.0");
		controls.set("soundCategory_hostile", "1.0");
		controls.set("soundCategory_neutral", "1.0");
		controls.set("soundCategory_player", "1.0");
		controls.set("soundCategory_ambient", "1.0");
		controls.set("chatVisibility", "0");
		controls.set("chatColors", "true");
		controls.set("chatLinks", "true");
		controls.set("chatLinksPrompt", "true");
		controls.set("chatOpacity", "1.0");
		controls.set("key_key.attack", "-100");
		controls.set("key_key.use", "-99");
		controls.set("key_key.forward", "44");
		controls.set("key_key.left", "16");
		controls.set("key_key.back", "31");
		controls.set("key_key.right", "32");
		controls.set("key_key.jump", "57");
		controls.set("key_key.sneak", "29");
		controls.set("key_key.drop", "19");
		controls.set("key_key.inventory", "18");
		controls.set("key_key.chat", "28");
		controls.set("key_key.playerlist", "15");
		controls.set("key_key.pickItem", "-98");
		controls.set("key_key.command", "53");
		controls.set("key_key.sprint", "42");
		controls.set("key_key.screenshot", "60");
		controls.set("key_key.togglePerspective", "63");
		controls.set("key_key.smoothCamera", "0");
		controls.set("key_key.streamStartStop", "64");
		controls.set("key_key.streamPauseUnpause", "65");
		controls.set("key_key.streamCommercial", "0");
		controls.set("key_key.streamToggleMic", "0");
		controls.set("key_key.fullscreen", "87");
		controls.set("key_key.spectatorOutlines", "0");
		controls.set("key_key.hotbar.1", "2");
		controls.set("key_key.hotbar.2", "3");
		controls.set("key_key.hotbar.3", "4");
		controls.set("key_key.hotbar.4", "5");
		controls.set("key_key.hotbar.5", "6");
		controls.set("key_key.hotbar.6", "7");
		controls.set("key_key.hotbar.7", "8");
		controls.set("key_key.hotbar.8", "9");
		controls.set("key_key.hotbar.9", "10");
		controls.set("resourcePacks", "[]");
		controls.set("guiScale", "2");
		controls.set("mouseSensitivity", "0.5");
		controls.set("fov", "0.0");
		controls.set("maxFps", "260");
		controls.set("lang", Controller.tweaks.get("lang", "en"));
	}

	private static void controlsFirstSet13() {
		controls.set("soundCategory_master", "0.6");
		controls.set("soundCategory_music", "0.3");
		controls.set("soundCategory_record", "1.0");
		controls.set("soundCategory_weather", "1.0");
		controls.set("soundCategory_block", "1.0");
		controls.set("soundCategory_hostile", "1.0");
		controls.set("soundCategory_neutral", "1.0");
		controls.set("soundCategory_player", "1.0");
		controls.set("soundCategory_ambient", "1.0");
		controls.set("chatVisibility", "0");
		controls.set("chatColors", "true");
		controls.set("chatLinks", "true");
		controls.set("chatLinksPrompt", "true");
		controls.set("chatOpacity", "1.0");
		controls.set("key_key.attack", "key.mouse.left");
		controls.set("key_key.use", "key.mouse.right");
		controls.set("key_key.forward", "key.keyboard.w");
		controls.set("key_key.left", "key.keyboard.a");
		controls.set("key_key.back", "key.keyboard.s");
		controls.set("key_key.right", "key.keyboard.d");
		controls.set("key_key.jump", "key.keyboard.space");
		controls.set("key_key.sneak", "key.keyboard.left.control");
		controls.set("key_key.sprint", "key.keyboard.left.shift");
		controls.set("key_key.drop", "key.keyboard.r");
		controls.set("key_key.inventory", "key.keyboard.e");
		controls.set("key_key.chat", "key.keyboard.enter");
		controls.set("key_key.playerlist", "key.keyboard.tab");
		controls.set("key_key.pickItem", "key.mouse.middle");
		controls.set("key_key.command", "key.keyboard.slash");
		controls.set("key_key.screenshot", "key.keyboard.f2");
		controls.set("key_key.togglePerspective", "key.keyboard.f5");
		controls.set("key_key.smoothCamera", "key.keyboard.unknown");
		controls.set("key_key.fullscreen", "key.keyboard.f11");
		controls.set("key_key.spectatorOutlines", "key.keyboard.unknown");
		controls.set("key_key.swapHands", "key.keyboard.f");
		controls.set("key_key.saveToolbarActivator", "key.keyboard.unknown");
		controls.set("key_key.loadToolbarActivator", "key.keyboard.x");
		controls.set("key_key.advancements", "key.keyboard.l");
		controls.set("key_key.hotbar.1", "key.keyboard.1");
		controls.set("key_key.hotbar.2", "key.keyboard.2");
		controls.set("key_key.hotbar.3", "key.keyboard.3");
		controls.set("key_key.hotbar.4", "key.keyboard.4");
		controls.set("key_key.hotbar.5", "key.keyboard.5");
		controls.set("key_key.hotbar.6", "key.keyboard.6");
		controls.set("key_key.hotbar.7", "key.keyboard.7");
		controls.set("key_key.hotbar.8", "key.keyboard.8");
		controls.set("key_key.hotbar.9", "key.keyboard.9");
		controls.set("key_of.key.zoom", "key.keyboard.c");
		controls.set("resourcePacks", "[]");
		controls.set("guiScale", "2");
		controls.set("mouseSensitivity", "0.5");
		controls.set("fov", "0.0");
		controls.set("maxFps", "260");
		controls.set("lang", Controller.tweaks.get("lang", "en"));
	}
}