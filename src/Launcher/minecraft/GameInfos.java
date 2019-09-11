package Launcher.minecraft;

import java.io.File;
import java.util.ArrayList;

import Launcher.minecraft.util.GameDirGenerator;

public class GameInfos
{

    private String serverName;

    private File gameDir;

    private GameTweak[] tweaks;

    private GameVersion gameVersion;

    public GameInfos(String serverName, GameVersion gameVersion, GameTweak[] tweaks)
    {
        this(serverName, GameDirGenerator.createGameDir(serverName), gameVersion, tweaks);
    }

    public GameInfos(String serverName, File gameDir, GameVersion gameVersion, GameTweak[] tweaks)
    {
        this.serverName = serverName;
        this.gameDir = gameDir;
        this.gameVersion = gameVersion;
        this.tweaks = tweaks;

        if (tweaks != null)
        {
            boolean forge = false;
            boolean shaderOrOptifine = false;

            for (GameTweak tweak : tweaks)
                if (tweak.equals(GameTweak.FORGE))
                    forge = true;
                else if (tweak.equals(GameTweak.OPTIFINE) || tweak.equals(GameTweak.SHADER))
                    shaderOrOptifine = true;
            
            if (shaderOrOptifine && forge)
            {
                System.out.println("[OpenLauncherLib] [WARNING] You selected Forge tweak with Optifine/Shader, they are ONLY FOR VANILLA, the game wil probably not start, so for security, Optifine/Shader was/were disabled");

                ArrayList<GameTweak> tweakList = new ArrayList<GameTweak>();

                for (GameTweak tweak : tweaks)
                    if (!tweak.equals(GameTweak.OPTIFINE) && !tweak.equals(GameTweak.SHADER))
                        tweakList.add(tweak);

                this.tweaks = tweakList.toArray(new GameTweak[tweakList.size()]);
            }
        }
    }

    public String getServerName()
    {
        return serverName;
    }

    public File getGameDir()
    {
        return this.gameDir;
    }

    public GameVersion getGameVersion()
    {
        return gameVersion;
    }

    public GameTweak[] getGameTweaks()
    {
        return tweaks;
    }

    public boolean hasGameTweak(GameTweak tweak)
    {
        for (GameTweak gameTweak : tweaks)
            if (gameTweak.equals(tweak))
                return true;

        return false;
    }
}
