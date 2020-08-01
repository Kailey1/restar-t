package cooldudes.restart.model;


public class Entry implements Comparable<Entry>{

    private int mood;
    private boolean goalMet;
    private String triggers, anything;
    private long time;

    public Entry(){}

    public Entry(long d){
        this.time = d;
        this.goalMet = true;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public boolean isGoalMet() {
        return goalMet;
    }

    public void setGoalMet(boolean goalMet) {
        this.goalMet = goalMet;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(Entry entry) {
        // returns 0 (same), 1 (puts o higher), -1 (puts this higher)
        if (time > entry.time){
            return 1;
        } else {
            return -1;
        }
    }

    public String getAnything() {
        return anything;
    }

    public void setAnything(String anything) {
        this.anything = anything;
    }
}
