package eu.ncodes.discordbot.nextends;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class BotExtend {

    private String token;
    private DiscordApi bot;

    public void initializeBot(){
        this.bot = new DiscordApiBuilder().setToken(this.token).setAllIntents().login().join();
    }
    public void connect(){
        this.disconnect();
        this.initializeBot();
    }
    public void disconnect(){
        this.bot.disconnect();
    }

}
