package eu.ncodes.discordbot.nextends;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.permission.Permissions;

public class BotExtend {

    private String token;
    private String prefix;
    private DiscordApi bot;

    public void initializeBot(){
        this.bot = new DiscordApiBuilder().setToken(this.token).setAllIntents().login().join();
        System.out.println(this.prefix + " > Connection URL: " + this.bot.createBotInvite(Permissions.fromBitmask(8)));
    }
    public void connect(){
        this.disconnect();
        this.initializeBot();
    }
    public void disconnect(){
        this.bot.disconnect();
    }

    public String getToken(){ return this.token; }
    public String getPrefix(){ return this.prefix; }
    public DiscordApi getBot(){ return this.bot; }

    public void setToken(String token){ this.token = token; }
    public void setPrefix(String prefix){ this.prefix = prefix; }
    public void setBot(DiscordApi bot){ this.bot = bot; }

}
