package taskmanagement.task;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long  taskId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String author;

    protected Comment(){}

    public Comment(Long taskId, String text, String author) {
        this.taskId = taskId;
        this.text = text;
        this.author = author;
    }

    public Long getId() {return id;}
    public Long getTaskId() {return taskId;}
    public String getText() {return text;}
    public String getAuthor() {return author;}

}
