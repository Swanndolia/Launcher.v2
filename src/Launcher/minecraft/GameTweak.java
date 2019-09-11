package Launcher.minecraft;

public abstract class GameTweak
{

    public static final String LAUNCHWRAPPER_MAIN_CLASS = "net.minecraft.launchwrapper.Launch";

    public static final GameTweak FORGE = new GameTweak()
    {
        @Override
        public String getName()
        {
            return "FML Tweaker";
        }

        @Override
        public String getTweakClass(GameInfos infos)
        {
            if (infos.getGameVersion().getGameType().equals(GameType.V1_8_HIGHER))
                return "net.minecraftforge.fml.common.launcher.FMLTweaker";
            else
                return "cpw.mods.fml.common.launcher.FMLTweaker";
        }
    };

    public static final GameTweak OPTIFINE = new GameTweak()
    {
        @Override
        public String getName()
        {
            return "Optifine Tweaker";
        }

        @Override
        public String getTweakClass(GameInfos infos)
        {
            return "optifine.OptiFineTweaker";
        }
    };

    public static final GameTweak SHADER = new GameTweak()
    {
        @Override
        public String getName()
        {
            return "Shader Tweaker";
        }

        @Override
        public String getTweakClass(GameInfos infos)
        {
            if (infos.getGameVersion().getName().contains("1.8"))
                return "shadersmod.launch.SMCTweaker";
            else
                return "shadersmodcore.loading.SMCTweaker";
        }
    };

    public abstract String getName();

    public abstract String getTweakClass(GameInfos infos);
}
