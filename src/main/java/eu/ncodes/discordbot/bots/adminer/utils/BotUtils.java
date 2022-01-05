package eu.ncodes.discordbot.bots.adminer.utils;

import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class BotUtils {

    public static boolean hasnCodesAIRole(Server server, User user){

        for(Role item : user.getRoles(server)){
            if(item.getIdAsString().equals(DiscordDefaults.rolenCodesAI))
                return true;
        }
        return false;

    }

    public static BotExtend getBotById(String id){
        if(DiscordUtils.emoter.getBot().getYourself().getIdAsString().equals(id)){
            return DiscordUtils.emoter;
        }
        else if(DiscordUtils.supporter.getBot().getYourself().getIdAsString().equals(id)){
            return DiscordUtils.supporter;
        }
        else if(DiscordUtils.player.getBot().getYourself().getIdAsString().equals(id)){
            return DiscordUtils.player;
        }
        else if(DiscordUtils.tester.getBot().getYourself().getIdAsString().equals(id)){
            return DiscordUtils.tester;
        }
        else if(DiscordUtils.challenger.getBot().getYourself().getIdAsString().equals(id)){
            return DiscordUtils.challenger;
        }
        else if(DiscordUtils.adminer.getBot().getYourself().getIdAsString().equals(id)){
            return DiscordUtils.adminer;
        }
        else{
            return null;
        }
    }

}
