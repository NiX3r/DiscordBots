package eu.ncodes.discordbot.bots.giveawayer.instances;

public class Member {

    private long userId;
    private int messagePoints;
    private int boostPoints;
    private int invitePoints;
    private int callPoints;

    public Member(long userId, int messagePoints, int boostPoints, int invitePoints, int callPoints){
        this.userId = userId;
        this.messagePoints = messagePoints;
        this.boostPoints = boostPoints;
        this.invitePoints = invitePoints;
        this.callPoints = callPoints;
    }

    // GETTERS
    public long getUserId() {
        return this.userId;
    }
    public int getMessagePoints() {
        return messagePoints;
    }
    public int getBoostPoints() {
        return boostPoints;
    }
    public int getInvitePoints() {
        return invitePoints;
    }
    public int getCallPoints() {
        return callPoints;
    }

    // INCREMENTS
    public void incrementMessagePoints(int value){
        this.messagePoints += value;
    }
    public void incrementBoostPoints(int value){
        this.boostPoints += value;
    }
    public void incrementInvitePoints(int value){
        this.invitePoints += value;
    }
    public void incrementCallPoints(int value){
        this.callPoints += value;
    }

    // DECREMENTS
    public void decrementMessagePoints(int value){
        this.messagePoints -= value;
    }
    public void decrementBoostPoints(int value){
        this.boostPoints -= value;
    }
    public void decrementInvitePoints(int value){
        this.invitePoints -= value;
    }
    public void decrementCallPoints(int value){
        this.callPoints -= value;
    }

}
