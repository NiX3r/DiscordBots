package eu.ncodes.discordbot.bots.supporter.listeners;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;

public class nSlashCommandCreateListener {

    public static void On(SlashCommandCreateEvent event){

        switch (event.getSlashCommandInteraction().getCommandName()){
            case "channel":
                OnChannel(event.getSlashCommandInteraction().getOptionChannelValueByName("CHANNEL").get());
        }

    }

    private static void OnChannel(ServerChannel channel){

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setContent("Hi :wave: \nIf you want to start support ticket don't be shy and click button below!");
        messageBuilder.addComponents(
                ActionRow.of(Button.success("ncodes-support", "Click me"))
        );
        messageBuilder.send(channel.asTextChannel().get());

    }

}
