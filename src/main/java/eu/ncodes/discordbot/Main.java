package eu.ncodes.discordbot;

import eu.ncodes.discordbot.bots.adminer.Adminer;
import eu.ncodes.discordbot.bots.challenger.Challenger;
import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.player.Player;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.tester.Tester;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.threads.ShutdownThread;
import eu.ncodes.discordbot.utils.DiscordTokens;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        LogSystem.log("PROGRAM", "Program started. Initializing...", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        if(args.length == 1)
            if(args[0].equals("-test"))
                DiscordUtils.isTest = true;

        DiscordUtils.bots = new HashMap<String, BotExtend>();

        DiscordUtils.bots.put("supporter", new Supporter(DiscordTokens.getSupporter(), true));
        DiscordUtils.bots.put("adminer", new Adminer(DiscordTokens.getAdminer()));
        DiscordUtils.bots.put("challenger", new Challenger(DiscordTokens.getChallanger()));
        DiscordUtils.bots.put("emoter", new Emoter(DiscordTokens.getEmoter(), true));
        DiscordUtils.bots.put("player", new Player(DiscordTokens.getPlayer()));
        DiscordUtils.bots.put("tester", new Tester(DiscordTokens.getTester()));

        for(String key : DiscordUtils.bots.keySet()){
            DiscordUtils.bots.get(key).initializeBot();
        }

        LogSystem.log("PROGRAM", "Initialize finished", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    }

}