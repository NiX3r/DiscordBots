package eu.ncodes.discordbot.utils;

import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.test.Test;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class DiscordUtils {

    public static Supporter supporter;
    public static Test tester;

    public static boolean hasRole(Server server, User user, String roleId){
        return server.getRoleById(roleId).get().hasUser(user);
    }

}
