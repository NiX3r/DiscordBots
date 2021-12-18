package eu.ncodes.discordbot.bots.supporter;

import eu.ncodes.discordbot.bots.supporter.instances.nSupport;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageComponentCreateListener;
import eu.ncodes.discordbot.bots.supporter.listeners.nMessageCreateListener;
import eu.ncodes.discordbot.bots.supporter.utils.Defaults;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.permission.Permissions;

import java.util.ArrayList;
import java.util.Arrays;

public class Supporter {

    private String token;
    private DiscordApi bot;
    private int supportIndex;
    private ArrayList<nSupport> supports;

    private final String PREFIX = "Supporter";

    public Supporter(String token){
        this.token = token;
        supportIndex = 0;
        this.supports = new ArrayList<nSupport>();
        initializeBot();
    }
    public Supporter(String token, ArrayList<nSupport> supports){
        this.token = token;
        supportIndex = 0;
        this.supports = supports;
        initializeBot();
    }

    public void initializeBot(){

        this.bot = new DiscordApiBuilder().setToken(this.token).setAllIntents().login().join();

        this.bot.addMessageCreateListener(new nMessageCreateListener());
        this.bot.addMessageComponentCreateListener(new nMessageComponentCreateListener());

    }
    public void connect(){
        this.disconnect();
        this.initializeBot();
    }
    public void disconnect(){
        this.bot.disconnect();
    }
    public void incrementSupportsIndex(){ this.supportIndex++; }
    public void addSupport(nSupport support){
        this.supports.add(support);
    }

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

}
