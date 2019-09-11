package Launcher.minecraft;

public class GameFolder
{

    public static final GameFolder BASIC = new GameFolder("assets", "libs", "natives", "minecraft.jar");

    private String assetsFolder;

    private String libsFolder;

    private String nativesFolder;

    private String mainJar;

    public GameFolder(String assetsFolder, String libsFolder, String nativesFolder, String mainJar)
    {
        this.assetsFolder = assetsFolder;
        this.libsFolder = libsFolder;
        this.nativesFolder = nativesFolder;
        this.mainJar = mainJar;
    }

    public String getAssetsFolder()
    {
        return assetsFolder;
    }

    public String getLibsFolder()
    {
        return libsFolder;
    }

    public String getNativesFolder()
    {
        return nativesFolder;
    }

    public String getMainJar()
    {
        return mainJar;
    }

}
