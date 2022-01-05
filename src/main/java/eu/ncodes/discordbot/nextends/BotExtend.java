package eu.ncodes.discordbot.nextends;

import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Permissions;

public class BotExtend {

    private String token;
    private String prefix;
    private DiscordApi bot;

    public void initializeBot(){
        this.bot = new DiscordApiBuilder().setToken(this.token).setAllIntents().login().join();
        LogSystem.log(getPrefix(), "bot is ready on : " + this.bot.createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        initializeLogListeners();
        LogSystem.log(getPrefix(), "bot initialized and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }
    public void initializeLogListeners(){
        this.bot.addLostConnectionListener(listener -> {
            LogSystem.log(getPrefix(), "lost connection", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
        this.bot.addReconnectListener(listener -> {
            LogSystem.log(getPrefix(), "reconnecting", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
        this.bot.addResumeListener(listener -> {
            LogSystem.log(getPrefix(), "resuming", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        });
    }
    public void connect(){
        this.disconnect();
        this.initializeBot();
    }
    public void disconnect(){
        this.bot.disconnect();
    }
    public void replyMessage(long serverId, long channelId, long messageId, String message){
        getBot().getServerById(serverId).ifPresent(server -> {
            server.getTextChannelById(channelId).ifPresent(channel -> {
                channel.getMessageById(messageId).thenAccept(msg ->{
                    msg.reply(message);
                });
            });
        });
    }

    public String getToken(){ return this.token; }
    public String getPrefix(){ return this.prefix; }
    public DiscordApi getBot(){ return this.bot; }

    public void setToken(String token){ this.token = token; }
    public void setPrefix(String prefix){ this.prefix = prefix; }
    public void setBot(DiscordApi bot){ this.bot = bot; }

}
