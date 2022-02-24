package eu.ncodes.discordbot.bots.giveawayer.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.giveawayer.Giveawayer;
import eu.ncodes.discordbot.bots.giveawayer.instances.Member;
import eu.ncodes.discordbot.bots.giveawayer.utils.Cache;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.supporter.instances.nMessage;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.utils.FileUtils;
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

    Listener class for listening message
    and add points to users
*/

public class nMessageCreateListener implements MessageCreateListener {

    private Cache cache;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        cache = ((Giveawayer)DiscordUtils.bots.get("giveawayer")).getCache();

        if(!event.getMessageAuthor().isBotUser()){

            if(cache.isMemberExists(event.getMessageAuthor().getId())){

                ((Giveawayer)DiscordUtils.bots.get("giveawayer")).getCache().getMemberByUserId(event.getMessageAuthor().getId()).incrementMessagePoints(
                        ((Giveawayer)DiscordUtils.bots.get("giveawayer")).getCache().giveMessagePoints
                );

            }
            else {

                Member member = new Member(event.getMessageAuthor().getId());
                member.incrementMessagePoints(
                        ((Giveawayer)DiscordUtils.bots.get("giveawayer")).getCache().giveMessagePoints
                );
                ((Giveawayer)DiscordUtils.bots.get("giveawayer")).getCache().addMember(member);

            }

        }

    }

}
