package eu.ncodes.discordbot.bots.supporter;

import eu.ncodes.discordbot.bots.supporter.listeners.nMessageComponentCreateListener;
import eu.ncodes.discordbot.bots.supporter.listeners.nSlashCommandCreateListener;
import eu.ncodes.discordbot.bots.supporter.utils.Defaults;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Supporter {

    private String TOKEN;
    private DiscordApi BOT;

    private final String PREFIX = "Supporter";

    public Supporter(String token){
        this.TOKEN = token;
        InitializeBot();
    }
    public void InitializeBot(){

        this.BOT = new DiscordApiBuilder().setToken(this.TOKEN).login().join();

        Defaults.CreateDefaultSupportMessage();

        this.BOT.addSlashCommandCreateListener(event -> nSlashCommandCreateListener.On(event));
        this.BOT.addMessageComponentCreateListener(event -> nMessageComponentCreateListener.On(event));

    }
    public void Connect(){
        this.Disconnect();
        this.InitializeBot();
    }
    public void Disconnect(){
        this.BOT.disconnect();
    }

    public DiscordApi GetAPI(){
        return this.BOT;
    }

}
