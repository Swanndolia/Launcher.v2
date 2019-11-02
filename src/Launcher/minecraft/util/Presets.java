package Launcher.minecraft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import Launcher.util.Saver;
import application.Controller;

public class Presets {
	
	public static void presetSet() throws IOException {
		
		if (new File(Controller.dir, "/options.txt").isFile() && new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + "/custom/options.txt").isFile()) {
				replaceFile(new File(Controller.dir, "/options.txt"), new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + "/custom/options.txt"));
				replaceFile(new File(Controller.dir, "/optionsof.txt"), new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + "/custom/optionsof.txt"));
			}
		
		if (!new File(Controller.dir, "/servers.dat").isFile())
			copyFile(new File(Controller.dir, "/presets/servers.dat"), new File(Controller.dir, "/servers.dat"));
		if (!new File(Controller.dir, "/options.txt").isFile())
			copyFile(new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + "/options.txt"), new File(Controller.dir, "/options.txt"));
		
		replaceFile(new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + '/' + Controller.tweaks.get("graphics") + "/optionsof.txt"), new File(Controller.dir, "/optionsof.txt"));
		
		File dirFrom = new File(Controller.dir, "/options.txt");
		File tmp = new File(Controller.dir, "/tmp.txt");
		
		BufferedReader fichier = new BufferedReader(new FileReader(dirFrom)); 
		BufferedWriter fichierW = new BufferedWriter(new FileWriter(tmp)); 
		String str; 
		str = fichier.readLine( ); 

		while (str != null){ 
			str=str.replaceAll(":","="); 
			fichierW.write(str);
			fichierW.write('\n'); 
			fichierW.flush(); 
			str = fichier.readLine(); 
		}
		fichierW.close(); 
		fichier.close(); 
		tmp.renameTo(dirFrom); 
		
		Saver AP_OPTIONS = new Saver(tmp);
		Saver AP_OPTIONSPRESET = new Saver(new File(Controller.dir, "/presets/" + Controller.tweaks.get("version") + '/' + Controller.tweaks.get("graphics") + "/options.txt"));
		
		AP_OPTIONS.set("renderDistance", AP_OPTIONSPRESET.get("renderDistance"));
		AP_OPTIONS.set("particles", AP_OPTIONSPRESET.get("particles"));
		AP_OPTIONS.set("fboEnable", AP_OPTIONSPRESET.get("fboEnable"));
		AP_OPTIONS.set("ao", AP_OPTIONSPRESET.get("ao"));
		AP_OPTIONS.set("lastServer", AP_OPTIONSPRESET.get("lastServer"));
		AP_OPTIONS.set("useVbo", AP_OPTIONSPRESET.get("useVbo"));
		AP_OPTIONS.set("mipmapLevels", AP_OPTIONSPRESET.get("mipmapLevels"));
		AP_OPTIONS.set("allowBlockAlternatives", AP_OPTIONSPRESET.get("allowBlockAlternatives"));
		AP_OPTIONS.set("entityShadows", AP_OPTIONSPRESET.get("entityShadows"));

		str = "";
		fichier = new BufferedReader(new FileReader(tmp)); 
		fichierW = new BufferedWriter(new FileWriter(dirFrom));  
		str = fichier.readLine( ); 
		while (str != null){ 
			str=str.replaceAll("=",":"); 
			fichierW.write(str); 
			fichierW.write('\n');
			fichierW.flush(); 
			str = fichier.readLine(); 
		}
		fichierW.close();  
		fichier.close(); 
		tmp.renameTo(dirFrom); 
		new File(Controller.dir, "/tmp.txt").delete();
	}
	
	public static void copyFile(File from, File to) throws IOException {
	    Files.copy(from.toPath(), to.toPath());
	} 
	
	public static void replaceFile(File from, File to) throws IOException {
	    Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
	} 
}