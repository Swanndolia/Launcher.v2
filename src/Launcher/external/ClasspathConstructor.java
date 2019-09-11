package Launcher.external;

import java.io.File;
import java.util.List;

import Launcher.util.explorer.FileList;

public class ClasspathConstructor extends FileList {
	public ClasspathConstructor() {
		super();
	}

	public ClasspathConstructor(List<File> classPath) {
		super(classPath);
	}

	public String make() {
		String classPath = "";

		for (int i = 0; i < files.size(); i++)
			classPath += files.get(i).getAbsolutePath() + (i + 1 == files.size() ? "" : File.pathSeparator);

		return classPath;
	}
}
