package eu.ncodes.discordbot.bots.emoter;

import eu.ncodes.discordbot.bots.emoter.instances.nReaction;
import eu.ncodes.discordbot.bots.emoter.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.bots.emoter.listeners.nReactionAddListener;
import eu.ncodes.discordbot.bots.emoter.listeners.nReactionRemoveListener;
import eu.ncodes.discordbot.bots.emoter.utils.FileUtils;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

public class Emoter extends BotExtend {

    private List<nReaction> reactionList;

    public Emoter(String token, boolean loadCache){
        setToken(token);
        setPrefix("nEmoter");

        if(loadCache){
            FileUtils.loadCache(cache ->{
                if(cache == null)
                    reactionList = new ArrayList<nReaction>();
                else
                    this.reactionList = cache;
            });
        }
        else {
            reactionList = new ArrayList<nReaction>();
        }

        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void initializeBot(){
        setBot(new DiscordApiBuilder().setToken(getToken()).setAllIntents().login().join());
        LogSystem.log(getPrefix(), "bot is ready on : " + getBot().createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        initializeLogListeners();
        getBot().addReactionAddListener(new nReactionAddListener());
        getBot().addReactionRemoveListener(new nReactionRemoveListener());
        getBot().addMessageCreateListener(new nMessageCreateListener());
        DiscordUtils.emoter.getBot().updateActivity(ActivityType.WATCHING, (DiscordUtils.emoter.getReactionList().size() == 1 ? "1 reaction" : DiscordUtils.emoter.getReactionList().size() + " reactions"));
        LogSystem.log(getPrefix(), "bot initialized and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }

    public List<nReaction> getReactionList(){
        return this.reactionList;
    }

    public nReaction getListedReaction(String emoji, long channelId, long messageId){
        for(nReaction reaction : reactionList){
            if(reaction.getEmoji().equals(emoji) && reaction.getChannelId() == channelId && reaction.getMessageId() == messageId){
                return reaction;
            }
        }
        return null;
    }

    public int isInListedReaction(String emoji, long channelId, long messageId, long roleId){
        for(int i = 0; i < reactionList.size(); i++){
            if(reactionList.get(i).getEmoji().equals(emoji) &&
                    reactionList.get(i).getChannelId() == channelId &&
                    reactionList.get(i).getMessageId() == messageId &&
                    reactionList.get(i).getRoleId() == roleId){
                return i;
            }
        }
        return -1;
    }

    public void addListedReaction(nReaction reaction){
        this.reactionList.add(reaction);
    }

    public void removeListedReaction(int index){
        this.reactionList.remove(index);
    }

    public void saveCache(){
        FileUtils.saveCache(callback -> {
            if(callback != null){
                LogSystem.log(getPrefix(), "cannot save cache file", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            }
        });
    }

}
