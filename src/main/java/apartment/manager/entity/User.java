package apartment.manager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;
    @Transient
    private Long userId; // TODO: This didn't work, find a way to drop user ID from users table

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
