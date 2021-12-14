package eu.ncodes.discordbot;

import eu.ncodes.discordbot.bots.supporter.Supporter;
import eu.ncodes.discordbot.utils.DiscordTokens;
import eu.ncodes.discordbot.utils.DiscordUtils;

public class Main {

    public static void main(String[] args) {

        DiscordUtils.SUPPORTER = new Supporter(DiscordTokens.SUPPORTER());

    }

}
