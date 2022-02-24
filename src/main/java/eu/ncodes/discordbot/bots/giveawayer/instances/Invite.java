package eu.ncodes.discordbot.bots.giveawayer.instances;

public class Invite {

    private String link;
    private long owner;
    private int joinCount;

    public Invite(String link, long owner, int joinCount){
        this.link = link;
        this.owner = owner;
        this.joinCount = joinCount;
    }

    // GETTERS
    public String getLink() {
        return link;
    }
    public long getOwner() {
        return owner;
    }
    public int getJoinCount() {
        return joinCount;
    }

    public void incrementCount(){
        this.joinCount++;
    }

}
