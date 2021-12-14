package eu.ncodes.discordbot.bots.supporter.listeners;

import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;

public class nMessageComponentCreateListener {

    public static void On(MessageComponentCreateEvent event){

        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();

        if(customId.equals("ncodes-support")){

            StartSupport(messageComponentInteraction);

        }

    }

    private static void StartSupport(MessageComponentInteraction messageComponentInteraction){
        // TODO - Start support
        System.out.println("START SUPPORT");
    }

}
