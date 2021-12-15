package eu.ncodes.discordbot.bots.supporter.instances;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class nSupport {

    private long owner;
    private int id;
    private LocalDateTime created;
    private HashMap<Long, String> members;
    private ArrayList<nMessage> messages;

    public nSupport(long owner, int id){
        this.owner = owner;
        this.id = id;
        this.created = LocalDateTime.now();
        this.members = new HashMap<Long, String>();
        this.messages = new ArrayList<nMessage>();
    }

    public long getOwner(){return this.owner;}
    public int getId(){return this.id;}
    public LocalDateTime getCreated(){return this.created;}
    public HashMap<Long,String> getMembers(){return this.members;}
    public ArrayList<nMessage> getMessages(){return this.messages;}

}
