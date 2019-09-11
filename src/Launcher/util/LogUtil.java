package Launcher.util;

import Launcher.LanguageManager;

public final class LogUtil
{

    public static void message(boolean err, String... messages)
    {
        String message = "[OpenLauncherLib] " + LanguageManager.lang(messages);

        if (err)
            System.err.println(message);
        else
            System.out.println(message);
    }

    public static void rawInfo(String message)
    {
        System.out.println("[OpenLauncherLib] " + message);
    }

    public static void rawErr(String message)
    {
        System.err.println("[OpenLauncherLib] " + message);
    }

    public static void info(String... messages)
    {
        message(false, messages);
    }

    public static void err(String... messages)
    {
        message(true, messages);
    }
}
