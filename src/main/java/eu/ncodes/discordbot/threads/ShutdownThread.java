package eu.ncodes.discordbot.threads;

import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;

public class ShutdownThread extends Thread{

    @Override
    public void run(){

        DiscordUtils.supporter.saveCache();
        LogSystem.log("Closing program", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        LogSystem.saveLogs();

    }

}
