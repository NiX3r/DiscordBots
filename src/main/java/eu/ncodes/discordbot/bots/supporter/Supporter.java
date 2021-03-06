package eu.ncodes.discordbot.bots.supporter;

import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageComponentCreateListener;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.bots.supporter.utils.FileUtils;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;

import java.util.ArrayList;

public class Supporter extends BotExtend {

    private int supportIndex;
    private ArrayList<nSupport> supports;

    public Supporter(String token, boolean loadCache){
        setToken(token);
        setPrefix("nSupporter");

        supportIndex = 0;

        if(loadCache){
            FileUtils.loadCache(cache ->{
                if(cache == null)
                    this.supports = new ArrayList<nSupport>();
                else
                    this.supports = cache;
            });
        }
        else {
            this.supports = new ArrayList<nSupport>();
        }
        LogSystem.log("nSupporter", " instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void initializeBot(){

        setBot(new DiscordApiBuilder().setToken(getToken()).setAllIntents().login().join());
        LogSystem.log(getPrefix(), "bot is ready on : " + getBot().createBotInvite(Permissions.fromBitmask(8)), new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

        getBot().addMessageCreateListener(new nMessageCreateListener());
        getBot().addMessageComponentCreateListener(new nMessageComponentCreateListener());
        initializeLogListeners();

        String status = this.getSupports().size() == 1 ? (this.getSupports().size() + " ticket") : (this.getSupports().size() + " tickets");
        this.getBot().updateActivity(ActivityType.WATCHING, status);

        LogSystem.log(DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }
    public void saveCache(){
        FileUtils.saveCache(callback -> {
            if(callback != null){
                LogSystem.log(DiscordUtils.bots.get( "supporter" + ( DiscordUtils.isTest ? "-test" : "" ) ).getPrefix(), "cannot save cache file", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
            }
        });
    }
    public void incrementSupportsIndex(){ if(this.supportIndex == Integer.MAX_VALUE) this.supportIndex = 0; else this.supportIndex++; }
    public void addSupport(nSupport support){
        this.supports.add(support);
    }
    public void removeSupport(nSupport support){ this.supports.remove(support); }

    public int getSupportsIndex(){ return this.supportIndex; }
    public ArrayList<nSupport> getSupports() { return this.supports; }
    public nSupport getSupportById(int id){
        for(nSupport sup : this.supports){
            if(sup.getId() == id)
                return sup;
        }
        return null;
    }
    public nSupport getSupportByOwnerId(long ownerId){
        for(nSupport sup : this.supports){
            if(sup.getOwnerId() == ownerId)
                return sup;
        }
        return null;
    }

    public void setSupports(ArrayList<nSupport> supports){ this.supports = supports; }

}
