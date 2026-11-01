package taskmanagement.account;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    @Column(unique = true, nullable = false)
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String username) {
        this.email = username;
    }

    @Column(nullable = false)
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return  password;
    }

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected  AppUser() {}
}
