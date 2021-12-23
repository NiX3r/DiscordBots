package eu.ncodes.discordbot.bots.supporter.instances;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

public class nMessage {

    private long id;
    private long owner;
    private LocalDateTime created;
    private String content;
    private HashMap<String, String> attachments;

    public nMessage(long id, long owner, Instant created, String content){
        this.id = id;
        this.owner = owner;
        this.created = LocalDateTime.ofInstant(created, ZoneOffset.UTC);
        this.content = content;
        this.attachments = new HashMap<String, String>();
    }

    public void addAttachment(String key, String fileName){
        this.attachments.put(key, fileName);
    }

    public long getID(){
        return this.id;
    }
    public long getOwner(){
        return this.owner;
    }
    public LocalDateTime getCreated(){
        return this.created;
    }
    public String getContent(){
        return this.content;
    }
    public HashMap<String, String> getAttachments() { return this.attachments; }

}
