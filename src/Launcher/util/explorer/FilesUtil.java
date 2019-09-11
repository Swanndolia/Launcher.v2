package Launcher.util.explorer;

import java.io.File;
import java.util.ArrayList;

import Launcher.FailException;

public class FilesUtil
{
    public static ArrayList<File> listRecursive(File directory)
    {
        ArrayList<File> files = new ArrayList<File>();
        File[] fs = directory.listFiles();
        if (fs == null)
            return files;

        for (File f : fs)
        {
            if (f.isDirectory())
                files.addAll(listRecursive(f));

            files.add(f);
        }

        return files;
    }

    public static File get(File root, String file)
    {
        File f = new File(root, file);
        if (!f.exists())
            throw new FailException("Given file/directory doesn't exist !");

        return f;
    }

    public static File dir(File d)
    {
        if (!d.isDirectory())
            throw new FailException("Given directory is not one !");

        return d;
    }

    public static File dir(File root, String dir)
    {
        return dir(get(root, dir));
    }

    public static File[] list(File dir)
    {
        File[] files = dir(dir).listFiles();

        return files == null ? new File[0] : files;
    }
}
