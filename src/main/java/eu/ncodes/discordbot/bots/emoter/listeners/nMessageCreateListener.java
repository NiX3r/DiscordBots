package eu.ncodes.discordbot.bots.emoter.listeners;

import eu.ncodes.discordbot.bots.emoter.Emoter;
import eu.ncodes.discordbot.bots.emoter.instances.nReaction;
import eu.ncodes.discordbot.utils.DiscordDefaults;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.emoji.CustomEmoji;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

/*
    Class nMessageCreateListener

    Listener class for listening message commands

    Includes:
    - set : set listener to give or take role
    - unset : unset listener to give or take role
*/

public class nMessageCreateListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        String[] splitter = event.getMessage().getContent().split(" ");

        /*
            Checks if it's nCodes supporter command
        */
        if(splitter[0].equals("n!e")){
            LogSystem.log(DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "command catch by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

            /*
                Checks if sender has admin role
            */
            if(DiscordUtils.hasRole(event.getServer().get(), event.getMessageAuthor().asUser().get(), DiscordDefaults.roleAdmin)){
                /*
                    Checks if command is in correct format
                */
                if(splitter.length == 6 &&
                        event.getMessage().getMentionedChannels().size() == 1 &&
                        event.getMessage().getMentionedRoles().size() == 1 &&
                        (splitter[1].equals("give") || splitter[1].equals("take"))){

                    nReaction reaction = new nReaction(splitter[3], event.getMessage().getMentionedChannels().get(0).getId(), Long.valueOf(splitter[5]), event.getMessage().getMentionedRoles().get(0).getId(), true);

                    if(splitter[1].equals("give"))
                        reaction.setGiveRole(true);
                    else
                        reaction.setGiveRole(false);

                    int index = ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).isInListedReaction(splitter[3], event.getMessage().getMentionedChannels().get(0).getId(), Long.valueOf(splitter[5]), event.getMessage().getMentionedRoles().get(0).getId());
                    /*
                        Checks if reaction is in list ? add it : remove it
                    */
                    if(index == -1){
                        ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).addListedReaction(reaction);
                        try{
                            event.getServer().get().getTextChannelById(reaction.getChannelId()).get().getMessageById(reaction.getMessageId()).join().addReaction(reaction.getEmoji());
                            event.getServer().get().getTextChannelById(reaction.getChannelId()).get().getMessageById(reaction.getMessageId()).join().addReaction(event.getMessage().getCustomEmojis().get(0));
                        }catch (Exception ex){}
                        event.getMessage().reply("Reaction successfully added into list.");
                    }
                    else {
                        ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).removeListedReaction(index);
                        try{
                            event.getServer().get().getTextChannelById(reaction.getChannelId()).get().getMessageById(reaction.getMessageId()).join().removeReactionByEmoji(reaction.getEmoji());
                            event.getServer().get().getTextChannelById(reaction.getChannelId()).get().getMessageById(reaction.getMessageId()).join().removeReactionByEmoji(event.getMessage().getCustomEmojis().get(0));
                        }catch (Exception ex){}
                        event.getMessage().reply("Reaction successfully removed into list.");
                    }

                    int size = ((Emoter)DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) )).getReactionList().size();
                    DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getBot().updateActivity(ActivityType.WATCHING, (size == 1 ? "1 reaction" : size + " reactions"));
                    LogSystem.log(DiscordUtils.bots.get( "emoter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "end of command by '" + event.getMessageAuthor().getName() + "'", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

                }
                else
                    event.getMessage().reply("Unfortunately, you typed command in bad format! Correct format looks like this: " +
                            "\n```" +
                            "n!e [give | take] <mentioned role> <emoji> <mentioned text channel> <message id>" +
                            "```");
            }


        }
    }

}
