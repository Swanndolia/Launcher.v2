package Launcher.minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Launcher.LaunchException;
import Launcher.external.ClasspathConstructor;
import Launcher.external.ExternalLaunchProfile;
import Launcher.util.LogUtil;
import Launcher.util.explorer.Explorer;

public class MinecraftLauncher {

	public static ExternalLaunchProfile createExternalProfile(GameInfos infos, GameFolder folder, AuthInfos authInfos) throws LaunchException {
		LogUtil.info("mc-ext", infos.getGameVersion().getName());
		LogUtil.info("mc-check", infos.getGameDir().getAbsolutePath());

		if (authInfos == null) {
			throw new IllegalArgumentException("authInfos == null");
		}

		checkFolder(folder, infos.getGameDir());

		LogUtil.info("mc-cp");

		ClasspathConstructor constructor = new ClasspathConstructor();
		constructor.add(Explorer.dir(infos.getGameDir()).sub(folder.getLibsFolder()).allRecursive().files().match("^(.*\\.((jar)$))*$").get());
		constructor.add(Explorer.dir(infos.getGameDir()).get(folder.getMainJar()));

		String mainClass = infos.getGameTweaks() == null || infos.getGameTweaks().length == 0
				? infos.getGameVersion().getGameType().getMainClass(infos)
				: GameTweak.LAUNCHWRAPPER_MAIN_CLASS;
				
		String classpath = constructor.make();
		List<String> args = infos.getGameVersion().getGameType().getLaunchArgs(infos, folder, authInfos);
		List<String> vmArgs = new ArrayList<String>();
		vmArgs.add("-Djava.library.path="+ Explorer.dir(infos.getGameDir()).sub(folder.getNativesFolder()).get().getAbsolutePath());
		vmArgs.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
		vmArgs.add("-Dfml.ignorePatchDiscrepancies=true");

		if (infos.getGameTweaks() != null)
			for (GameTweak tweak : infos.getGameTweaks()) {
				args.add("--tweakClass");
				args.add(tweak.getTweakClass(infos));
			}

		ExternalLaunchProfile profile = new ExternalLaunchProfile(mainClass, classpath, vmArgs, args, true, infos.getServerName(), infos.getGameDir());

		LogUtil.info("done");

		return profile;
	}
	
	public static void checkFolder(GameFolder folder, File directory) throws FolderException {
		File assetsFolder = new File(directory, folder.getAssetsFolder());
		File libsFolder = new File(directory, folder.getLibsFolder());
		File nativesFolder = new File(directory, folder.getNativesFolder());
		File minecraftJar = new File(directory, folder.getMainJar());

		if (!assetsFolder.exists() || assetsFolder.listFiles() == null)
			throw new FolderException("Missing/Empty assets folder (" + assetsFolder.getAbsolutePath() + ")");
		else if (!libsFolder.exists() || libsFolder.listFiles() == null)
			throw new FolderException("Missing/Empty libraries folder (" + libsFolder.getAbsolutePath() + ")");
		else if (!nativesFolder.exists() || nativesFolder.listFiles() == null)
			throw new FolderException("Missing/Empty natives folder (" + nativesFolder.getAbsolutePath() + ")");
		else if (!minecraftJar.exists())
			throw new FolderException("Missing main jar (" + minecraftJar.getAbsolutePath() + ")");
	}
}
