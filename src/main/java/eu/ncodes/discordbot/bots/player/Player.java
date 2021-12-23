package eu.ncodes.discordbot.bots.player;

import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;

public class Player extends BotExtend {

    public Player(String token){
        setToken(token);
        setPrefix("nPlayer");
        LogSystem.log(getPrefix() + " instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

}
