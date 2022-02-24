package eu.ncodes.discordbot.bots.giveawayer.utils;

import eu.ncodes.discordbot.bots.giveawayer.instances.Member;
import eu.ncodes.discordbot.bots.giveawayer.instances.SteamKey;

import java.util.ArrayList;

public class Cache {

    public ArrayList<Member> members;
    public ArrayList<SteamKey> steamKeys;

    public int giveMessagePoints;
    public int giveBoostPoints;
    public int giveInvitePoints;
    public int giveCallPoints;

    public Cache(){

        giveMessagePoints = 5;
        giveBoostPoints = 1000;
        giveInvitePoints = 50;
        giveCallPoints = 15;

    }

    public Member getMemberByUserId(long id){
        for (Member member : this.members){
            if(member.getUserId() == id)
                return member;
        }
        return null;
    }

    public boolean isMemberExists(long id){
        for (Member member : this.members){
            if(member.getUserId() == id)
                return true;
        }
        return false;
    }

    public int getTotalPoints(){
        int total = 0;
        for (Member member : this.members){
            total += member.getTotalPoints();
        }
        return total;
    }

    public void addMember(Member member){
        this.members.add(member);
    }

}
