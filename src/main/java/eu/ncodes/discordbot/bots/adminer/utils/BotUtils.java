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
        for(String key : DiscordUtils.bots.keySet()){
            if(DiscordUtils.bots.get(key).getBot().getYourself().getIdAsString().equals(id))
                return DiscordUtils.bots.get(key);
        }
        return null;
    }

}
