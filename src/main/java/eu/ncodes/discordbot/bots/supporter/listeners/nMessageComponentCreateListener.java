package eu.ncodes.discordbot.bots.supporter.listeners;

import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
                messageComponentInteraction.asSelectMenuInteraction().get().acknowledge();
                LogSystem.log(DiscordUtils.supporter.getPrefix(), "user '" + messageComponentInteraction.getUser().getName() + "' select '" + messageComponentInteraction.asSelectMenuInteraction().get().getChosenOptions().get(0).getLabel() + "' as ticket topic", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                break;
            case "ncodes-support":
                startSupport(messageComponentInteraction.getServer().get(), messageComponentInteraction.getUser());
                messageComponentInteraction.asButtonInteraction().get().acknowledge();
                LogSystem.log(DiscordUtils.supporter.getPrefix(), "start new ticket for '" + messageComponentInteraction.getUser().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
                break;
        }

    }

    private void startSupport(Server server, User user){

        long id = server.createTextChannelBuilder()
                .setCategory(server.getChannelCategoryById(DiscordDefaults.categorySupport).get())
                .setName(DiscordUtils.supporter.getSupportsIndex() + "-" + user.getName())
                .setTopic("**Owner**: *" + user.getName() + "* | **Type**: *" + types.get(user.getId()))
                .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0, 1024))
                .addPermissionOverwrite(server.getRoleById(DiscordDefaults.roleAtMember).get(), Permissions.fromBitmask(1024))
                .addPermissionOverwrite(user, Permissions.fromBitmask(1024))
                .create().join().getId();

        user.addRole(server.getRoleById(DiscordDefaults.roleSupport).get());

        nSupport support = new nSupport(DiscordUtils.supporter.getSupportsIndex(), id, user.getId(), user.getName(), types.get(user.getId()));
        DiscordUtils.supporter.addSupport(support);
        DiscordUtils.supporter.incrementSupportsIndex();
        String status = DiscordUtils.supporter.getSupports().size() == 1 ? (DiscordUtils.supporter.getSupports().size() + " ticket") : (DiscordUtils.supporter.getSupports().size() + " tickets");
        DiscordUtils.supporter.getBot().updateActivity(ActivityType.WATCHING, status);

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#7900FF"))
                .setThumbnail("http://documents.ncodes.eu/support.jpg")
                .setTitle("Ticket System :tickets: ")
                .setDescription("Hi! " + user.getMentionTag() +  ":wave: \nPlease try to most specify your problem.\nWe'll respond as much quick as possible. :ok_hand: ")
                .addField("ID", String.valueOf(support.getId()))
                .addField("Type", support.getType())
                .addField("Owner", user.getName())
                .addField("Owner ID", user.getIdAsString())
                .addField("Created", support.getCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)));

        new MessageBuilder()
                .setEmbed(builder)
                .setContent(" " + user.getMentionTag() + " :ear: :ear: ")
                .send(server.getTextChannelById(id).get()).join();

    }

}
