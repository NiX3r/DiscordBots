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

import java.util.HashMap;

public class nMessageComponentCreateListener implements MessageComponentCreateListener {

    private HashMap<Long, String> types = new HashMap<Long, String>();

    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();

        switch (customId){
            case "ncodes-support-list":
                types.put(messageComponentInteraction.getUser().getId(), messageComponentInteraction.asSelectMenuInteraction().get().getChosenOptions().get(0).getLabel());
                break;
            case "ncodes-support":
                startSupport(messageComponentInteraction.getServer().get(), messageComponentInteraction.getUser());
                break;
        }

        // This must be here. We don't know why,
        // but if it's not here Discord will show
        // user some kind of error message. Due it
        // it's here forever. Sorry future me!
        event.getMessageComponentInteraction().createImmediateResponder().respond();
        return;

    }

    private void startSupport(Server server, User user){

        long id = server.createTextChannelBuilder()
                .setCategory(server.getChannelCategoryById("852878611378995230").get())
                .setName(DiscordUtils.SUPPORTER.getSupportsIndex() + "-" + user.getName())
                .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0, 1024))
                .addPermissionOverwrite(server.getRoleById("723490876898148352").get(), Permissions.fromBitmask(1024))
                .addPermissionOverwrite(user, Permissions.fromBitmask(1024))
                .create().join().getId();

        nSupport support = new nSupport(user.getId(), user.getName(), DiscordUtils.SUPPORTER.getSupportsIndex(), types.get(user.getId()));
        DiscordUtils.SUPPORTER.addSupport(support);
        types.remove(user.getId());
        DiscordUtils.SUPPORTER.incrementSupportsIndex();

        server.getTextChannelById(id).get().sendMessage( "Type: " + support.getType() + "\nPlease try to specify your problem!\n" + user.getMentionTag());

    }

}
