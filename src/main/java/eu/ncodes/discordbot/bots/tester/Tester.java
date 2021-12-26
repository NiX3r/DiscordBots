package eu.ncodes.discordbot.bots.tester;

import eu.ncodes.discordbot.bots.tester.listeners.PingCommandListener;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.permission.Permissions;

public class Tester extends BotExtend {

    public Tester(String token){
        setToken(token);
        setPrefix("nTester");
        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void initializeBot(){
        setBot(new DiscordApiBuilder().setToken(getToken()).login().join());
        LogSystem.log(getPrefix(), "bot is ready on : " + getBot().createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        getBot().addMessageCreateListener(event -> PingCommandListener.On(event));
        initializeLogListeners();
        LogSystem.log(getPrefix(), "bot initialized and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

}
