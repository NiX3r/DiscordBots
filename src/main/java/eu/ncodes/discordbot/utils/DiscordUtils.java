package eu.ncodes.discordbot.utils;

import eu.ncodes.discordbot.bots.adminer.Adminer;
import eu.ncodes.discordbot.bots.challenger.Challenger;
import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.player.Player;
import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.bots.tester.Tester;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class DiscordUtils {

    public static Supporter supporter;
    public static Tester tester;
    public static Adminer adminer;
    public static Challenger challenger;
    public static Player player;
    public static Emoter emoter;

    public static boolean hasRole(Server server, User user, String roleId){
        return server.getRoleById(roleId).get().hasUser(user);
    }

}
