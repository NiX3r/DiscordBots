package eu.ncodes.discordbot.threads;

import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;

public class ShutdownThread extends Thread{

    @Override
    public void run(){

        ((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).saveCache();
        ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).saveCache();
        LogSystem.log("PROGRAM", "Closing program", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        LogSystem.saveLogs();

    }

}
