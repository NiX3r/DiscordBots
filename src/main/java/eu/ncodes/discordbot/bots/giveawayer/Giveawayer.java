package eu.ncodes.discordbot.bots.giveawayer;

import eu.ncodes.discordbot.bots.giveawayer.instances.Member;
import eu.ncodes.discordbot.bots.giveawayer.instances.SteamKey;
import eu.ncodes.discordbot.bots.giveawayer.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.bots.giveawayer.timers.ActivityTimer;
import eu.ncodes.discordbot.bots.giveawayer.utils.Cache;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;

import java.util.ArrayList;
import java.util.Timer;

public class Giveawayer extends BotExtend {

    private Cache cache;
    private Timer activityTimer;

    public Giveawayer(String token, boolean loadCache){
        setToken(token);
        setPrefix("nGiveawayer");

        cache = new Cache();
        activityTimer = new Timer();

        cache.members = new ArrayList<Member>();
        cache.steamKeys = new ArrayList<SteamKey>();

        LogSystem.log(getPrefix(), "instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void initializeBot(){
        setBot(new DiscordApiBuilder().setToken(getToken()).setAllIntents().login().join());
        LogSystem.log(getPrefix(), "bot is ready on : " + getBot().createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
        initializeLogListeners();
        getBot().addMessageCreateListener(new nMessageCreateListener());
        getBot().updateActivity(ActivityType.WATCHING, cache.getTotalPoints() + " points");
        activityTimer.schedule(new ActivityTimer(), 300000, 1);
        LogSystem.log(getPrefix(), "bot initialized and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    public Cache getCache(){
        return this.cache;
    }
    public Timer getActivityTimer(){
        return this.activityTimer;
    }

}
