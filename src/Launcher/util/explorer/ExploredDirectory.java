package Launcher.util.explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ExploredDirectory
{
    protected File directory;

    ExploredDirectory(File directory)
    {
        this.directory = directory;
    }

    public FileList allRecursive()
    {
        return new FileList(FilesUtil.listRecursive(directory));
    }

    public FileList list()
    {
        return new FileList(Arrays.asList(FilesUtil.list(this.directory)));
    }

    public ExploredDirectory sub(String directory)
    {
        return new ExploredDirectory(FilesUtil.dir(this.directory, directory));
    }

    public File get(String file)
    {
        return FilesUtil.get(this.directory, file);
    }
    
    public FileList subs()
    {
        File[] files = FilesUtil.list(this.directory);
        ArrayList<File> dirs = new ArrayList<File>();

        for (File f : files)
            if (f.isDirectory())
                dirs.add(f);

        return new FileList(dirs);
    }

    public FileList files()
    {
        File[] files = FilesUtil.list(this.directory);
        ArrayList<File> fs = new ArrayList<File>();

        for (File f : files)
            if (!f.isDirectory())
                fs.add(f);

        return new FileList(fs);
    }

    public File get()
    {
        return directory;
    }
}
