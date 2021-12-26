package eu.ncodes.discordbot.bots.emoter.listeners;

import eu.ncodes.discordbot.bots.emoter.instances.nReaction;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

public class nReactionRemoveListener implements ReactionRemoveListener {

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {

        event.getEmoji().asCustomEmoji().ifPresent(emoji -> {
            nReaction reaction = DiscordUtils.emoter.getListedReaction(emoji.getMentionTag(), event.getChannel().getId(), event.getMessageId());
            event.getServer().ifPresent(server -> {
                event.getUser().ifPresent(user -> {
                    on(reaction, user, server);
                });
            });
        });

        event.getEmoji().asUnicodeEmoji().ifPresent(emoji -> {
            nReaction reaction = DiscordUtils.emoter.getListedReaction(emoji, event.getChannel().getId(), event.getMessageId());
            event.getServer().ifPresent(server -> {
                event.getUser().ifPresent(user -> {
                    on(reaction, user, server);
                });
            });
        });

    }

    private void on(nReaction reaction, User user, Server server){

        LogSystem.log(DiscordUtils.emoter.getPrefix(), "start giving/taking role to '" + user.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        if(reaction.isGiveRole()){
            server.getRoleById(reaction.getRoleId()).ifPresent(role -> {
                server.removeRoleFromUser(user, role).join();
                LogSystem.log(DiscordUtils.emoter.getPrefix(), "role took from '" + user.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            });
        }
        else{
            server.getRoleById(reaction.getRoleId()).ifPresent(role -> {
                server.addRoleToUser(user, role).join();
                LogSystem.log(DiscordUtils.emoter.getPrefix(), "role gave to '" + user.getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            });
        }

    }

}
