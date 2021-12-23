package eu.ncodes.discordbot;

import eu.ncodes.discordbot.bots.adminer.Adminer;
import eu.ncodes.discordbot.bots.challenger.Challenger;
import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.player.Player;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.tester.Tester;
import eu.ncodes.discordbot.threads.ShutdownThread;
import eu.ncodes.discordbot.utils.DiscordTokens;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;

public class Main {

    public static void main(String[] args) {

        LogSystem.log("Program started. Initializing...", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        DiscordUtils.supporter = new Supporter(DiscordTokens.getSupporter(), true);
        DiscordUtils.adminer = new Adminer(DiscordTokens.getAdminer());
        DiscordUtils.challenger = new Challenger(DiscordTokens.getChallanger());
        DiscordUtils.emoter = new Emoter(DiscordTokens.getEmoter());
        DiscordUtils.player = new Player(DiscordTokens.getPlayer());
        DiscordUtils.tester = new Tester(DiscordTokens.getTester());

        DiscordUtils.supporter.initializeBot();
        DiscordUtils.adminer.initializeBot();
        DiscordUtils.challenger.initializeBot();
        DiscordUtils.emoter.initializeBot();
        DiscordUtils.player.initializeBot();
        DiscordUtils.tester.initializeBot();

        LogSystem.log("Initialize finished", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    }

}