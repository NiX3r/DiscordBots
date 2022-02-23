package eu.ncodes.discordbot.bots.giveawayer.instances;

public class SteamKey {

    private String key;
    private String name;
    private double prize;

    public SteamKey(String key, String name, double prize){
        this.key = key;
        this.name = name;
        this.prize = prize;
    }

    // GETTERS
    public String getKey() {
        return key;
    }
    public String getName() {
        return name;
    }
    public double getPrize() {
        return prize;
    }

}
