package eu.ncodes.discordbot.bots.giveawayer;

import eu.ncodes.discordbot.bots.emoter.instances.nReaction;
import eu.ncodes.discordbot.bots.emoter.utils.FileUtils;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;

import java.util.ArrayList;

public class Giveawayer extends BotExtend {

    public Giveawayer(String token, boolean loadCache){
        setToken(token);
        setPrefix("nGiveawayer");

        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

}
