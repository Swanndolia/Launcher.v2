package Launcher.minecraft;

public class GameVersion
{
    private String name;

    private GameType gameType;

    public GameVersion(String name, GameType gameType)
    {
        this.name = name;
        this.gameType = gameType;
    }

    public String getName()
    {
        return this.name;
    }

    public GameType getGameType()
    {
        return gameType;
    }
}