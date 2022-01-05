package eu.ncodes.discordbot.bots.adminer.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.adminer.utils.BotUtils;
import eu.ncodes.discordbot.bots.supporter.instances.nMessage;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.utils.FileUtils;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
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
                LogSystem.log(DiscordUtils.supporter.getPrefix(), "reload command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
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
                            LogSystem.log(DiscordUtils.supporter.getPrefix(), "end of reload command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                            return;
                        }
                    }
                    event.getMessage().reply("You have to mention 1 user!");
                }
            }

            /*
                Checks if it's cache subcommand
            */
            else if(splitter[1].equals("cache")){
                LogSystem.log(DiscordUtils.supporter.getPrefix(), "cache command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                /*
                    Checks if sender has Admin role
                */
                if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                    onCache(event.getMessage().getChannel());
                    LogSystem.log(DiscordUtils.supporter.getPrefix(), "end of cache command", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                }
            }

        }
    }

    private void onReload(BotExtend bot){
        bot.disconnect();
        bot.initializeBot();
        LogSystem.log(DiscordUtils.supporter.getPrefix(), "reload done", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    /*
        Method onCache

        for send supporter bot cache as
        json format file

        Input variables:
        - TextChannel channel : get channel for sending cache
    */
    private void onCache(TextChannel channel){

        byte[] file = (new Gson().toJson(DiscordUtils.supporter.getSupports())).getBytes();

        new MessageBuilder()
                .setContent("Here is mine cache!")
                .addAttachment(file, "cache.json")
                .send(channel);

    }

}
