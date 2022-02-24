package eu.ncodes.discordbot.bots.giveawayer.timers;

import eu.ncodes.discordbot.bots.giveawayer.Giveawayer;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.entity.channel.ServerVoiceChannel;

import java.util.TimerTask;

public class VoiceTimer extends TimerTask {

    @Override
    public void run() {
        Giveawayer giveawayer = ((Giveawayer) DiscordUtils.bots.get("giveawayer"));
        for (ServerVoiceChannel channel : giveawayer.getBot().getServerVoiceChannels()){
            for(long id : channel.getConnectedUserIds()){
                ((Giveawayer) DiscordUtils.bots.get("giveawayer")).getCache().getMemberByUserId(id).incrementCallPoints(
                        ((Giveawayer) DiscordUtils.bots.get("giveawayer")).getCache().giveCallPoints
                );
            }
        }
    }

}
