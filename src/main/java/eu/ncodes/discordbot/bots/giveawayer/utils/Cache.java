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
    public int giveCallPerMinutePoints;

    public Cache(){

        giveMessagePoints = 5;
        giveBoostPoints = 1000;
        giveInvitePoints = 50;
        giveCallPerMinutePoints = 15;

    }

    public Member GetMemberByUserId(long id){
        for (Member member : this.members){
            if(member.getUserId() == id)
                return member;
        }
        return null;
    }

    public boolean IsMemberExists(long id){
        for (Member member : this.members){
            if(member.getUserId() == id)
                return true;
        }
        return false;
    }

    public void AddMember(Member member){
        this.members.add(member);
    }

}
