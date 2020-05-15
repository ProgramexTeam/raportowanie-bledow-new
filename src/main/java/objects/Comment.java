package objects;

public class Comment {
    private int ID;
    private int user_ID;
    private int stuff_ID;
    private String text;
    private String date;

    public Comment(int ID, int user_ID, int stuff_ID, String text, String date) {
        this.ID = ID;
        this.user_ID = user_ID;
        this.stuff_ID = stuff_ID;
        this.text = text;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public int getStuff_ID() {
        return stuff_ID;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
