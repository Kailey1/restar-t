package cooldudes.restart.model;


public class Entry implements Comparable<Entry>{


    private int mood;
    private boolean drank;
    private String triggers, anything;
    private long date;

    public Entry(){}

    public Entry(long d){
        this.date = d;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public boolean isDrank() {
        return drank;
    }

    public void setDrank(boolean drank) {
        this.drank = drank;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int compareTo(Entry entry) {
        // returns 0 (same), 1 (puts o higher), -1 (puts this higher)
        if (date > entry.date){
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
