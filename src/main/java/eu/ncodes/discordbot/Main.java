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

public class Main {

    public static void main(String[] args) {

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

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    }

}