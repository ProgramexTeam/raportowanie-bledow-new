package objects;

public class Project {
    private int id;
    private String title;
    private String description;

    public Project(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public Project() {
        this.id = 0;
        this.title = "";
        this.description = "";
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String categoryName) {
        this.description = categoryName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
