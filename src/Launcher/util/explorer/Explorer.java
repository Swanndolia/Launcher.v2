package Launcher.util.explorer;

import java.io.File;

public class Explorer extends ExploredDirectory
{

    public Explorer(File directory)
    {
        super(directory);
    }

    public void cd(File cd)
    {
        this.directory = cd;
    }

    public void cd(String cd)
    {
        this.directory = FilesUtil.get(directory, cd);
    }

    public static ExploredDirectory dir(String dir)
    {
        return dir(new File(dir));
    }

    public static ExploredDirectory dir(File dir)
    {
        return new ExploredDirectory(FilesUtil.dir(dir));
    }
}
