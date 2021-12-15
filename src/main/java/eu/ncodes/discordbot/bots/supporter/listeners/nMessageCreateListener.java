package eu.ncodes.discordbot.bots.supporter.listeners;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;


public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String[] splitter = event.getMessage().getContent().split(" ");

        if(splitter[0].equals("n!s")){

            if(splitter[1].equals("msg") || splitter[1].equals("message")){
                if(event.getMessage().getMentionedChannels().size() == 1){
                    onCreateMessage(event.getMessage().getMentionedChannels().get(0));
                    return;
                }
                event.getMessage().reply("You have to mention 1 text channel!");
            }

        }

    }

    private void onCreateMessage(ServerTextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#7900FF"))
                .setThumbnail("http://documents.ncodes.eu/support.jpg")
                .setTitle("Ticket System :tickets: ")
                .setDescription("Hi! :wave: \nDon't be shy and open new ticket by pressing a button below!")
                .addField("Rule #1", "Don't abuse ticket system")
                .addField("Rule #2", "After create ticket try to most specify your problem")
                .addField("To add member into ticket", "n!s member add <user id>")
                .addField("To close ticket", "n!s close");

        new MessageBuilder()
        .setEmbed(builder)
        .addComponents(
                ActionRow.of(SelectMenu.create("ncodes-support-list", "Please select topic of ticket", 1, 1,
                        Arrays.asList(
                                SelectMenuOption.create("Idea", "Selected topic : idea", "Select if you have some idea to discuss"),
                                SelectMenuOption.create("Bug", "Selected topic : bug", "Select if you found some bug to discuss")
                        ))),
                ActionRow.of(Button.success("ncodes-support", "Start a ticket"))
        )
        .send(channel);

    }

}