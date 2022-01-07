package eu.ncodes.discordbot.utils;

import eu.ncodes.discordbot.nextends.BotExtend;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.HashMap;

public class DiscordUtils {

    public static HashMap<String, BotExtend> bots;
    public static boolean isTest = false;

    public static boolean hasRole(Server server, User user, String roleId){
        return server.getRoleById(roleId).get().hasUser(user);
    }

}
