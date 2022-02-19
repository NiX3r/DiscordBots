package eu.ncodes.discordbot;

import eu.ncodes.discordbot.bots.adminer.Adminer;
import eu.ncodes.discordbot.bots.challenger.Challenger;
import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.giveawayer.Giveawayer;
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

        DiscordUtils.bots = new HashMap<String, BotExtend>();
        if(args.length > 1){
            if(args[0].equals("-test") && args.length > 1){
                DiscordUtils.isTest = true;
                for(int i = 1; i < args.length; i++){
                    switch (args[i]){
                        case "adminer":
                            DiscordUtils.bots.put("adminer", new Adminer(DiscordTokens.getAdminer()));
                            break;
                        case "supporter":
                            DiscordUtils.bots.put("supporter", new Supporter(DiscordTokens.getSupporter(), true));
                            break;
                        case "emoter":
                            DiscordUtils.bots.put("emoter", new Emoter(DiscordTokens.getEmoter(), true));
                            break;
                        case "challenger":
                            DiscordUtils.bots.put("challenger", new Challenger(DiscordTokens.getChallanger()));
                            break;
                        case "giveawayer":
                            DiscordUtils.bots.put("giveawayer", new Giveawayer(DiscordTokens.getGiveawayer(), true));
                            break;
                        case "tester":
                            DiscordUtils.bots.put("tester", new Tester(DiscordTokens.getTester()));
                            break;
                    }
                }
            }
        }
        else {
            DiscordUtils.bots.put("supporter", new Supporter(DiscordTokens.getSupporter(), true));
            DiscordUtils.bots.put("adminer", new Adminer(DiscordTokens.getAdminer()));
            DiscordUtils.bots.put("challenger", new Challenger(DiscordTokens.getChallanger()));
            DiscordUtils.bots.put("emoter", new Emoter(DiscordTokens.getEmoter(), true));
            DiscordUtils.bots.put("giveawayer", new Giveawayer(DiscordTokens.getGiveawayer(), true));
            DiscordUtils.bots.put("tester", new Tester(DiscordTokens.getTester()));
        }

        for(String key : DiscordUtils.bots.keySet()){
            DiscordUtils.bots.get(key).initializeBot();
        }

        LogSystem.log("PROGRAM", "Initialize finished", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    }

}