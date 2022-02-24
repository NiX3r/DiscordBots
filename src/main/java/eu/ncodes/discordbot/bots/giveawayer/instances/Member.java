package eu.ncodes.discordbot.bots.giveawayer.instances;

import java.time.LocalDateTime;

public class Member {

    private long userId;
    private LocalDateTime joinDate;
    private int messagePoints;
    private int boostPoints;
    private int invitePoints;
    private int callPoints;

    public Member(long userId){
        this.userId = userId;
        this.joinDate = LocalDateTime.now();
        this.messagePoints = this.boostPoints = this.invitePoints = this.callPoints = 0;
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
    public int getTotalPoints(){
        return this.getMessagePoints() + this.getBoostPoints() + this.getInvitePoints() + this.getCallPoints();
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
