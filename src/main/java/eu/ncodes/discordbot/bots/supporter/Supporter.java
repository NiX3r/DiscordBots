package eu.ncodes.discordbot.bots.supporter;

import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageComponentCreateListener;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.bots.supporter.utils.FileLog;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordUtils;
import eu.ncodes.discordbot.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import java.util.ArrayList;

public class Supporter extends BotExtend {

    private int supportIndex;
    private ArrayList<nSupport> supports;

    public Supporter(String token, boolean loadCache){
        setToken(token);
        setPrefix("Supporter");

        supportIndex = 0;

        if(loadCache){
            FileLog.loadCache(cache ->{
                if(cache == null)
                    this.supports = new ArrayList<nSupport>();
                else
                    this.supports = cache;
            });
        }
        else {
            this.supports = new ArrayList<nSupport>();
        }
        LogSystem.log("Supporter instance created", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());
    }

    @Override
    public void initializeBot(){

        setBot(new DiscordApiBuilder().setToken(getToken()).setAllIntents().login().join());

        getBot().addMessageCreateListener(new nMessageCreateListener());
        getBot().addMessageComponentCreateListener(new nMessageComponentCreateListener());
        initializeLogListeners();

        String status = this.getSupports().size() == 1 ? (this.getSupports().size() + " ticket") : (this.getSupports().size() + " tickets");
        this.getBot().updateActivity(ActivityType.WATCHING, status);

        LogSystem.log(DiscordUtils.supporter.getPrefix() + " bot initialize and turned on", new Throwable().getStackTrace()[0].getLineNumber(), new Throwable().getStackTrace()[0].getFileName(), new Throwable().getStackTrace()[0].getMethodName());

    }
    public void saveCache(){
        FileLog.saveCache(callback -> {
            if(callback != null){
                System.out.println("Cannot save cache into file!");
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
