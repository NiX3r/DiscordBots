package eu.ncodes.discordbot.bots.challenger.instances;

public class Challenge {

    private int id;
    private String name;
    private int maxPoints;
    private String script;

    public Challenge(int id, String name, int maxPoints, String script){
        this.id = id;
        this.name = name;
        this.maxPoints = maxPoints;
        this.script = script;
    }

    // GETTERS
    public int getId(){ return this.id; }
    public String getName() { return name; }
    public int getMaxPoints() { return maxPoints; }
    public String getScript() { return script; }

    // SETTERS


    public void setName(String name) { this.name = name; }
    public void setMaxPoints(int maxPoints) { this.maxPoints = maxPoints; }
    public void setScript(String script) { this.script = script; }

}
