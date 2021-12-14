package eu.ncodes.discordbot.bots.supporter.utils;

import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;

public class Defaults {

    public static void CreateDefaultSupportMessage(){

        SlashCommand slashCommand = SlashCommand.with("support", "Command for control Support bot", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "message", "Create default support message", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.CHANNEL, "CHANNEL", "Specify target channel")
                ))
        )).setDefaultPermission(false).createGlobal(DiscordUtils.SUPPORTER.GetAPI()).join();

    }

}
