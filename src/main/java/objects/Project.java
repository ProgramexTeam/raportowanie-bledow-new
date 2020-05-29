package objects;

public class Project {
    private int id;
    private int author_id;
    private String title;
    private String description;

    public Project(int id, int author_id, String title, String description) {
        this.id = id;
        this.author_id = author_id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAuthor_id() {
        return author_id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
