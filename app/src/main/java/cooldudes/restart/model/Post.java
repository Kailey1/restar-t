package cooldudes.restart.model;


public class Post implements Comparable<Post>{

    private String title, written, id;
    private long time;

    public Post(){}

    public Post(String i, String t, String w){
        this.id = i;
        this.written = w;
        this.time = System.currentTimeMillis();
        this.title = t;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(Post entry) {
        // returns 0 (same), 1 (puts o higher), -1 (puts this higher)
        if (time > entry.time){
            return -1;
        } else {
            return 1;
        }
    }

    public String getWritten() {
        return written;
    }

    public void setWritten(String written) {
        this.written = written;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
