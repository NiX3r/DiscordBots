package eu.ncodes.discordbot.bots.giveawayer.timers;

import eu.ncodes.discordbot.bots.giveawayer.Giveawayer;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.entity.activity.ActivityType;

import java.util.TimerTask;

public class ActivityTimer extends TimerTask {

    @Override
    public void run() {
        ((Giveawayer) DiscordUtils.bots.get("giveawayer")).getBot().updateActivity(
                ActivityType.WATCHING,
                ((Giveawayer) DiscordUtils.bots.get("giveawayer")).getCache().getTotalPoints() + " points"
        );
    }

}
