package eu.ncodes.discordbot.bots.challenger.instances;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Member {

    private long id;
    private String name;
    private LocalDateTime joinDate;
    private int points;

    public Member(long id, String name, LocalDateTime joinDate, int points){
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.points = points;
    }

    // GETTERS
    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getJoinDate() { return joinDate; }
    public int getPoints() { return points; }

    // SETTERS
    public void setName(String name) { this.name = name; }

    // OTHERS
    public void incrementPoints(int value){
        this.points += value;
    }

}
