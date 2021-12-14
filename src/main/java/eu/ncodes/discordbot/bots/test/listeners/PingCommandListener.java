package eu.ncodes.discordbot.bots.test.listeners;

import org.javacord.api.event.message.MessageCreateEvent;

public class PingCommandListener {

    public static void On(MessageCreateEvent event){

        if(event.getMessage().getContent().contains("!ping")){

            event.getChannel().sendMessage("Pong!");

        }

    }

}
