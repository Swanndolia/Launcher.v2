package Launcher;

import java.io.File;
import java.lang.reflect.Field;

import Launcher.util.LogUtil;

public class JavaUtil {
	
	public static String pathJava;
	public static Boolean javaCustom = false;

	public static String[] getSpecialArgs() {
		return new String[] { "-XX:-UseAdaptiveSizePolicy", "-XX:+UseConcMarkSweepGC" };
	}

	public static String macDockName(String name) {
		return "-Xdock:name=" + name;
	}

	public static String getJavaCommand() {
		if (javaCustom) {
			LogUtil.info("jre-custom");
			return pathJava;
		} else {
		return System.getProperty("java.home") + File.separator + "bin"+ File.separator + "java";
		}
	}
		
	public static String setJavaCommandLauncher(String path) {
		return pathJava = path;
	}
	public static Boolean setJavaCustom(Boolean enabled) {
		return javaCustom = enabled;
	}
	
	public static void setLibraryPath(String path) throws Exception {
		System.setProperty("java.library.path", path);

		Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
		fieldSysPath.setAccessible(true);
		fieldSysPath.set(null, null);
	}
}
