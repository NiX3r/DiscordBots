package eu.ncodes.discordbot.bots.tester;

import eu.ncodes.discordbot.bots.tester.listeners.PingCommandListener;
import eu.ncodes.discordbot.nextends.BotExtend;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Tester extends BotExtend {

    public Tester(String token){
        setToken(token);
        setPrefix("nTester");
    }

    @Override
    public void initializeBot(){
        setBot(new DiscordApiBuilder().setToken(getToken()).login().join());
        getBot().addMessageCreateListener(event -> PingCommandListener.On(event));
    }

}
