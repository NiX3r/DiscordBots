package eu.ncodes.discordbot.bots.challenger;

import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;

public class Challenger extends BotExtend {

    public Challenger(String token){
        setToken(token);
        setPrefix("nChallenger");
        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

}
