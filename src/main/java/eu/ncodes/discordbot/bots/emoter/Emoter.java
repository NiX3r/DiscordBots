package eu.ncodes.discordbot.bots.emoter;

import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;

public class Emoter extends BotExtend {

    public Emoter(String token){
        setToken(token);
        setPrefix("nEmoter");
        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

}
