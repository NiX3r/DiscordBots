package eu.ncodes.discordbot.threads;

import eu.ncodes.discordbot.utils.DiscordUtils;

public class ShutdownThread extends Thread{

    @Override
    public void run(){

        System.out.println("here");
        DiscordUtils.supporter.saveCache();

    }

}
