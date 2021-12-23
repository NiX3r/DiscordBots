package eu.ncodes.discordbot.bots.adminer;

import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;

public class Adminer extends BotExtend {

    public Adminer(String token){
        setToken(token);
        setPrefix("nAdminer");
        LogSystem.log(getPrefix() + " instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

}
