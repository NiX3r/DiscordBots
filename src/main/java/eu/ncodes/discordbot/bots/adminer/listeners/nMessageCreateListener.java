package eu.ncodes.discordbot.bots.adminer.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.adminer.utils.BotUtils;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/*
    Class nMessageCreateListener

    Listener class for listening message commands
    and messages in ticket channels to save them

    Includes:
    - message : create default message for creating new tickets
    - member : add or remove members into / from ticket
    - close : close and save current ticket
    - cache : send file which contains cache
*/

public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String[] splitter = event.getMessage().getContent().split(" ");

        /*
            Checks if it's nCodes supporter command && author is user
        */
        if(splitter[0].equals("n!a") && event.getMessageAuthor().isUser()){

            /*
                Checks if it's message subcommand
            */
            if(splitter[1].equals("r") || splitter[1].equals("reload")){
                LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "reload command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender has admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    /*
                    Checks if command contains 1 mention channel
                    */
                    if(event.getMessage().getMentionedUsers().size() == 1){
                        if(BotUtils.hasnCodesAIRole(event.getServer().get(), event.getMessage().getMentionedUsers().get(0))){
                            BotExtend bot = BotUtils.getBotById(event.getMessage().getMentionedUsers().get(0).getIdAsString());
                            if(bot == null){
                                event.getMessage().reply("You have to mention nCodes bot");
                                return;
                            }
                            onReload(bot);
                            LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "end of reload command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                            return;
                        }
                    }
                    event.getMessage().reply("You have to mention 1 user!");
                }
            }

            /*
                Checks if it's on subcommand
            */
            else if(splitter[1].equals("on")){
                LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "on command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender has admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    /*
                    Checks if command contains 1 mention channel
                    */
                    if(event.getMessage().getMentionedUsers().size() == 1){
                        if(BotUtils.hasnCodesAIRole(event.getServer().get(), event.getMessage().getMentionedUsers().get(0))){
                            BotExtend bot = BotUtils.getBotById(event.getMessage().getMentionedUsers().get(0).getIdAsString());
                            if(bot == null){
                                event.getMessage().reply("You have to mention nCodes bot");
                                return;
                            }
                            onOn(bot);
                            LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "end of on command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                            return;
                        }
                    }
                    event.getMessage().reply("You have to mention 1 user!");
                }

            }

            else if(splitter[1].equals("off")){
                LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "off command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender has admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    /*
                    Checks if command contains 1 mention channel
                    */
                    if(event.getMessage().getMentionedUsers().size() == 1){
                        if(BotUtils.hasnCodesAIRole(event.getServer().get(), event.getMessage().getMentionedUsers().get(0))){
                            BotExtend bot = BotUtils.getBotById(event.getMessage().getMentionedUsers().get(0).getIdAsString());
                            if(bot == null){
                                event.getMessage().reply("You have to mention nCodes bot");
                                return;
                            }
                            onOff(bot);
                            LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "end of off command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                            return;
                        }
                    }
                    event.getMessage().reply("You have to mention 1 user!");
                }
            }

            else if(splitter[1].equals("status") || splitter[1].equals("ping")){
                LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "ping command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender has admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    onPing(event.getMessage());
                    LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "end of ping command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                }
            }

            /*
                Checks if it's cache subcommand
            */
            else if(splitter[1].equals("cache")){
                LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "cache command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender has Admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    onCache(event.getMessage().getChannel());
                    LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "end of cache command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                }
            }

        }
    }

    private void onReload(BotExtend bot){
        bot.disconnect();
        bot.initializeBot();
        LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "reload done", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    private void onOn(BotExtend bot){
        bot.connect();
        LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    private void onOff(BotExtend bot){
        bot.disconnect();
        LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "turned off", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    private void onPing(Message message){
        DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getBot().getMessageById(message.getId(), message.getChannel()).join().reply("Ready to serve, sir!");
        DiscordUtils.bots.get( "challenger" + ( DiscordUtils.isTest ? "-test" : "" ) ).replyMessage(message.getServer().get().getId(), message.getChannel().getId(), message.getId(), "Ready to serve, sir!");
        DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) ).replyMessage(message.getServer().get().getId(), message.getChannel().getId(), message.getId(), "Ready to serve, sir!");
        DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).replyMessage(message.getServer().get().getId(), message.getChannel().getId(), message.getId(), "Ready to serve, sir!");
        DiscordUtils.bots.get( "players" + ( DiscordUtils.isTest ? "-test" : "" ) ).replyMessage(message.getServer().get().getId(), message.getChannel().getId(), message.getId(), "Ready to serve, sir!");
        DiscordUtils.bots.get( "tester" + ( DiscordUtils.isTest ? "-test" : "" ) ).replyMessage(message.getServer().get().getId(), message.getChannel().getId(), message.getId(), "Ready to serve, sir!");
        LogSystem.log(DiscordUtils.bots.get( "adminer" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "bots ponged", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    /*
        Method onCache

        for send supporter bot cache as
        json format file

        Input variables:
        - TextChannel channel : get channel for sending cache
    */
    private void onCache(TextChannel channel){

        byte[] file = (new Gson().toJson(((Supporter)DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getSupports())).getBytes();

        new MessageBuilder()
                .setContent("Here is mine cache!")
                .addAttachment(file, "cache.json")
                .send(channel);

    }

}
