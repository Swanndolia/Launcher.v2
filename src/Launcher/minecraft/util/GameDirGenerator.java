package Launcher.minecraft.util;

import java.io.File;

public class GameDirGenerator
{
    public static File createGameDir(String serverName)
    {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            return new File(System.getProperty("user.home") + "\\AppData\\Roaming\\." + serverName);
        else if (os.contains("mac"))
            return new File(System.getProperty("user.home") + "/Library/Application Support/" + serverName);
        else
            return new File(System.getProperty("user.home") + "/." + serverName);
    }
}
