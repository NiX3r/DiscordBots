package eu.ncodes.discordbot.bots.supporter.instances;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class nMessage {

    private long id;
    private long owner;
    private LocalDateTime created;
    private String content;

    public nMessage(long id, long owner, Instant created, String content){
        this.id = id;
        this.owner = owner;
        this.created = LocalDateTime.ofInstant(created, ZoneOffset.UTC);
        this.content = content;
    }

    public long GetID(){
        return this.id;
    }
    public long GetOwner(){
        return this.owner;
    }
    public LocalDateTime GetCreated(){
        return this.created;
    }
    public String GetContent(){
        return this.content;
    }

}
