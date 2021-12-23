package eu.ncodes.discordbot.bots.player;

import eu.ncodes.discordbot.nextends.BotExtend;

public class Player extends BotExtend {

    public Player(String token){
        setToken(token);
        setPrefix("nPlayer");
    }

}
