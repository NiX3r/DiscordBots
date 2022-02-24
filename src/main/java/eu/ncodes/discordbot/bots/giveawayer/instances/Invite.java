package eu.ncodes.discordbot.bots.giveawayer.instances;

public class Invite {

    private String code;
    private long owner;
    private int joinCount;

    public Invite(String code, long owner, int joinCount){
        this.code = code;
        this.owner = owner;
        this.joinCount = joinCount;
    }

    // GETTERS
    public String getCode() {
        return this.code;
    }
    public long getOwner() {
        return this.owner;
    }
    public int getJoinCount() {
        return this.joinCount;
    }

    public void incrementCount(){
        this.joinCount++;
    }

}
