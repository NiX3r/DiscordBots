package eu.ncodes.discordbot;

import eu.ncodes.discordbot.bots.adminer.Adminer;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.tester.Test;
import eu.ncodes.discordbot.threads.ShutdownThread;
import eu.ncodes.discordbot.utils.DiscordTokens;
import eu.ncodes.discordbot.utils.DiscordUtils;

public class Main {

    public static void main(String[] args) {

        DiscordUtils.supporter = new Supporter(DiscordTokens.getSupporter(), true);
        DiscordUtils.adminer = new Adminer(DiscordTokens.getAdminer());
        DiscordUtils.tester = new Test(DiscordTokens.getTester());

        DiscordUtils.supporter.initializeBot();
        DiscordUtils.adminer.initializeBot();

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    }

}