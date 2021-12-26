package eu.ncodes.discordbot.bots.emoter.instances;

import org.javacord.api.entity.emoji.Emoji;

public class nReaction {

    private String emoji;
    private long channelId;
    private long messageId;
    private long roleId;
    private boolean giveRole;

    public nReaction(String emoji, long channelId, long messageId, long roleId, boolean giveRole){
        this.emoji = emoji;
        this.channelId = channelId;
        this.messageId = messageId;
        this.roleId = roleId;
        this.giveRole = giveRole;
    }

    public String getEmoji(){ return this.emoji; }
    public long getChannelId(){ return this.channelId; }
    public long getMessageId(){ return this.messageId; }
    public long getRoleId(){ return this.roleId; }
    public boolean isGiveRole(){ return this.giveRole; }

    public void setEmoji(String emoji){ this.emoji = emoji; }
    public void setChannelId(long channelId){ this.channelId = channelId; }
    public void setRoleId(long groupId){ this.roleId = groupId; }
    public void setMessageId(long messageId){ this.messageId = messageId; }
    public void setGiveRole(boolean giveRole){ this.giveRole = giveRole; }

}
