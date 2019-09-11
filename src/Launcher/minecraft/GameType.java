package Launcher.minecraft;

import java.io.File;
import java.util.ArrayList;

import Launcher.minecraft.util.ConnectToServer;
import Launcher.util.LogUtil;

public abstract class GameType
{
    public static final GameType V1_7_10 = new GameType()
    {
        @Override
        public String getName()
        {
            return "1.7.10";
        }

        @Override
        public String getMainClass(GameInfos infos)
        {
            return "net.minecraft.client.main.Main";
        }

        @Override
        public ArrayList<String> getLaunchArgs(GameInfos infos, GameFolder folder, AuthInfos authInfos)
        {
            ArrayList<String> arguments = new ArrayList<String>();
            
            if (ConnectToServer.isActived()) {
            	LogUtil.info("connect-server");
            	arguments.add("--server=" + ConnectToServer.getServer());
            	arguments.add("--port=" + ConnectToServer.getServerPort());
            }

            arguments.add("--username=" + authInfos.getUsername());

            arguments.add("--accessToken");
            arguments.add(authInfos.getAccessToken());

            if (authInfos.getClientToken() != null)
            {
                arguments.add("--clientToken");
                arguments.add(authInfos.getClientToken());
            }

            arguments.add("--version");
            arguments.add(infos.getGameVersion().getName());

            arguments.add("--gameDir");
            arguments.add(infos.getGameDir().getAbsolutePath());

            arguments.add("--assetsDir");
            File assetsDir = new File(infos.getGameDir(), folder.getAssetsFolder());
            arguments.add(assetsDir.getAbsolutePath());

            arguments.add("--assetIndex");
            arguments.add(infos.getGameVersion().getName());

            arguments.add("--userProperties");
            arguments.add("{}");

            arguments.add("--uuid");
            arguments.add(authInfos.getUuid());

            arguments.add("--userType");
            arguments.add("legacy");

            return arguments;
        }
    };

    public static final GameType V1_8_HIGHER = new GameType()
    {
        @Override
        public String getName()
        {
            return "1.8 or higher";
        }

        @Override
        public String getMainClass(GameInfos infos)
        {
            return "net.minecraft.client.main.Main";
        }

        @Override
        public ArrayList<String> getLaunchArgs(GameInfos infos, GameFolder folder, AuthInfos authInfos)
        {
            ArrayList<String> arguments = new ArrayList<String>();
            
            if (ConnectToServer.isActived()) {
            	LogUtil.info("connect-server");
            	arguments.add("--server=" + ConnectToServer.getServer());
            	arguments.add("--port=" + ConnectToServer.getServerPort());
            }

            arguments.add("--username=" + authInfos.getUsername());

            arguments.add("--accessToken");
            arguments.add(authInfos.getAccessToken());

            if (authInfos.getClientToken() != null)
            {
                arguments.add("--clientToken");
                arguments.add(authInfos.getClientToken());
            }

            arguments.add("--version");
            arguments.add(infos.getGameVersion().getName());

            arguments.add("--gameDir");
            arguments.add(infos.getGameDir().getAbsolutePath());

            arguments.add("--assetsDir");
            File assetsDir = new File(infos.getGameDir(), folder.getAssetsFolder());
            arguments.add(assetsDir.getAbsolutePath());

            arguments.add("--assetIndex");

            String version = infos.getGameVersion().getName();

            int first = version.indexOf('.');
            int second = version.lastIndexOf('.');

            if (first != second)
            {
                version = version.substring(0, version.lastIndexOf('.'));
            }

            arguments.add(version);

            arguments.add("--userProperties");
            arguments.add("{}");

            arguments.add("--uuid");
            arguments.add(authInfos.getUuid());

            arguments.add("--userType");
            arguments.add("legacy");

            return arguments;
        }
    };

    public abstract String getName();

    public abstract String getMainClass(GameInfos infos);

    public abstract ArrayList<String> getLaunchArgs(GameInfos infos, GameFolder folder, AuthInfos authInfos);
}
