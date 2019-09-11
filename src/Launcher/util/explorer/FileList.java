package Launcher.util.explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileList
{
    protected List<File> files;

    public FileList()
    {
        this.files = new ArrayList<File>();
    }

    public FileList(List<File> files)
    {
        this.files = files;
    }

    public void add(File... files)
    {
        this.add(Arrays.asList(files));
    }

    public void add(List<File> files)
    {
        this.files.addAll(files);
    }

    public void add(FileList list)
    {
        this.add(list.get());
    }
    
    public FileList match(String regex)
    {
        ArrayList<File> matching = new ArrayList<File>();

        for (File f : files)
            if (f.getName().matches(regex))
                matching.add(f);

        return new FileList(matching);
    }

    public FileList dirs()
    {
        ArrayList<File> dirs = new ArrayList<File>();

        for (File f : files)
            if (f.isDirectory())
                dirs.add(f);

        return new FileList(dirs);
    }

    public FileList files()
    {
        ArrayList<File> files = new ArrayList<File>();

        for (File f : this.files)
            if (!f.isDirectory())
                files.add(f);

        return new FileList(files);
    }

    public List<File> get()
    {
        return files;
    }
}
