package Launcher.external;

import java.io.File;
import java.util.List;

public class ExternalLaunchProfile
{

    private String mainClass;

    private String classPath;

    private List<String> vmArgs;

    private List<String> args;

    private boolean redirectErrorStream = true;

    private String macDockName;

    private File directory;

    public ExternalLaunchProfile(String mainClass, String classPath)
    {
        this(mainClass, classPath, null, null);
    }

    public ExternalLaunchProfile(String mainClass, String classPath, List<String> vmArgs, List<String> args)
    {
        this(mainClass, classPath, vmArgs, args, false, null, null);
    }

    public ExternalLaunchProfile(String mainClass, String classPath, List<String> vmArgs, List<String> args, boolean redirectErrorStream, String macDockName, File directory)
    {
        this.mainClass = mainClass;
        this.classPath = classPath;
        System.out.print(classPath);
        this.vmArgs = vmArgs;
        this.args = args;
        this.redirectErrorStream = redirectErrorStream;
        this.macDockName = macDockName;
        this.directory = directory;
    }

    public String getMainClass()
    {
        return mainClass;
    }

    public void setMainClass(String mainClass)
    {
        this.mainClass = mainClass;
    }

    public String getClassPath()
    {
        return classPath;
    }

    public void setClassPath(String classPath)
    {
        this.classPath = classPath;
    }

    public List<String> getVmArgs()
    {
        return vmArgs;
    }

    public void setVmArgs(List<String> vmArgs)
    {
        this.vmArgs = vmArgs;
    }

    public List<String> getArgs()
    {
        return args;
    }

    public void setArgs(List<String> args)
    {
        this.args = args;
    }

    public boolean isRedirectErrorStream()
    {
        return redirectErrorStream;
    }

    public void setRedirectErrorStream(boolean redirectErrorStream)
    {
        this.redirectErrorStream = redirectErrorStream;
    }

    public String getMacDockName()
    {
        return macDockName;
    }

    public void setMacDockName(String macDockName)
    {
        this.macDockName = macDockName;
    }

    public File getDirectory()
    {
        return directory;
    }

    public void setDirectory(File directory)
    {
        this.directory = directory;
    }
}
