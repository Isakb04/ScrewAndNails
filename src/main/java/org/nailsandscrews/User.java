package org.nailsandscrews;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {
    private int userid;
    private String username;
    private String password;
    private String type;
    private String create_time;

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getID() {
        return userid;
    }

    public void setID(int ID) {
        this.userid = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

}
