package eu.ncodes.discordbot.bots.supporter.listeners;

import com.google.gson.Gson;
import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public class nMessageComponentCreateListener implements MessageComponentCreateListener {

    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();

        if(customId.equals("ncodes-support")){

            StartSupport(messageComponentInteraction.getServer().get(), messageComponentInteraction.getUser());

        }

        System.out.println("End of event");

    }

    private static void StartSupport(Server server, User user){

        long id = server.createTextChannelBuilder()
                .setCategory(server.getChannelCategoryById("852878611378995230").get())
                .setName(user.getName())
                .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0, 1024))
                .addPermissionOverwrite(server.getRoleById("723490876898148352").get(), Permissions.fromBitmask(1024))
                .addPermissionOverwrite(user, Permissions.fromBitmask(1024))
                .create().join().getId();

        nSupport support = new nSupport(user.getId(), DiscordUtils.SUPPORTER.getSupportsIndex());
        DiscordUtils.SUPPORTER.addSupport(support);

        server.getTextChannelById(id).get().sendMessage(user.getMentionTag() + " \n" + new Gson().toJson(support));

    }

}
