package eu.ncodes.discordbot.bots.supporter;

import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageComponentCreateListener;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.bots.supporter.utils.Defaults;
import eu.ncodes.discordbot.bots.supporter.utils.FileLog;
import eu.ncodes.discordbot.nextends.BotExtend;
import eu.ncodes.discordbot.utils.DiscordUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;

import java.util.ArrayList;
import java.util.Arrays;

public class Supporter extends BotExtend {

    private String token;
    private DiscordApi bot;
    private int supportIndex;
    private ArrayList<nSupport> supports;

    private final String prefix = "Supporter";

    public Supporter(String token, boolean loadCache){
        this.token = token;
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
    }

    @Override
    public void initializeBot(){

        this.bot = new DiscordApiBuilder().setToken(this.token).setAllIntents().login().join();

        this.bot.addMessageCreateListener(new nMessageCreateListener());
        this.bot.addMessageComponentCreateListener(new nMessageComponentCreateListener());

        String status = this.getSupports().size() == 1 ? (this.getSupports().size() + " ticket") : (this.getSupports().size() + " tickets");
        this.getAPI().updateActivity(ActivityType.WATCHING, status);

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

    public DiscordApi getAPI(){
        return this.bot;
    }
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
