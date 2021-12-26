package eu.ncodes.discordbot.bots.player;

import eu.ncodes.discordbot.bots.player.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.permission.Permissions;

public class Player extends BotExtend {

    public Player(String token){
        setToken(token);
        setPrefix("nPlayer");
        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void initializeBot(){

        setBot(new DiscordApiBuilder().setToken(getToken()).setAllIntents().login().join());
        LogSystem.log(getPrefix(), "bot is ready on : " + getBot().createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        getBot().addMessageCreateListener(new nMessageCreateListener());
        initializeLogListeners();

        LogSystem.log(DiscordUtils.supporter.getPrefix(), "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

}
