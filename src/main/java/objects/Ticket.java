package objects;

import java.io.Serializable;

public class Ticket implements Serializable {

    private int ticket_id;
    private int author_id;
    private int project_id;
    private String status;
    private String title;
    private String description;

    public Ticket(int ticket_id, int author_id, int project_id, String status, String title, String description) {
        this.ticket_id = ticket_id;
        this.author_id = author_id;
        this.project_id = project_id;
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public Ticket() {
        this.ticket_id = -1;
        this.author_id = -1;
        this.project_id = -1;
        this.status = null;
        this.title = null;
        this.description = null;
    }

    public void setId(int ticket_id) {
        this.ticket_id = ticket_id;
    }
    public int getId() {
        return ticket_id;
    }
    public int getAuthor_id() {
        return author_id;
    }
    public int getProject_id() {
        return project_id;
    }
    public String getStatus() {
        return status;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}
