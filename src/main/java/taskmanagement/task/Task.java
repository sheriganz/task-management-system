package taskmanagement.task;


import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private String author;

    @Column
    private String assignee;

    protected Task() {}

    public Task(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.CREATED;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public String getAuthor() { return author; }
    public String getAssignee() { return assignee; }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
