package eu.ncodes.discordbot.bots.test;

import eu.ncodes.discordbot.bots.test.listeners.PingCommandListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Test {

    private String TOKEN;
    private DiscordApi BOT;

    private final String PREFIX = "Tester";

    public Test(String token){
        this.TOKEN = token;
        InitializeBot();
    }

    public void InitializeBot(){
        this.BOT = new DiscordApiBuilder().setToken(this.TOKEN).login().join();
        this.BOT.addMessageCreateListener(event -> PingCommandListener.On(event));
    }

    public void Connect(){
        this.Disconnect();
        this.InitializeBot();
    }

    public void Disconnect(){
        this.BOT.disconnect();
    }

}
