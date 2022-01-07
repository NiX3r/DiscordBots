package eu.ncodes.discordbot.bots.emoter.listeners;

import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.emoter.instances.nReaction;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

public class nReactionAddListener implements ReactionAddListener {

    @Override
    public void onReactionAdd(ReactionAddEvent event) {

        event.getEmoji().asCustomEmoji().ifPresent(emoji -> {
            nReaction reaction = ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getListedReaction(emoji.getMentionTag(), event.getChannel().getId(), event.getMessageId());
            event.getServer().ifPresent(server -> {
                event.getUser().ifPresent(user -> {
                    on(reaction, user, server);
                });
            });
        });

        event.getEmoji().asUnicodeEmoji().ifPresent(emoji -> {
            nReaction reaction = ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getListedReaction(emoji, event.getChannel().getId(), event.getMessageId());
            event.getServer().ifPresent(server -> {
                event.getUser().ifPresent(user -> {
                    on(reaction, user, server);
                });
            });
        });

    }

    private void on(nReaction reaction, User user, Server server){

        LogSystem.log(DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "start giving/taking role to '" + user.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        if(reaction.isGiveRole()){
            server.getRoleById(reaction.getRoleId()).ifPresent(role -> {
                server.addRoleToUser(user, role).join();
                LogSystem.log(DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "role gave to '" + user.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            });
        }
        else{
            server.getRoleById(reaction.getRoleId()).ifPresent(role -> {
                server.removeRoleFromUser(user, role).join();
                LogSystem.log(DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "role took from '" + user.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            });
        }

    }

}
