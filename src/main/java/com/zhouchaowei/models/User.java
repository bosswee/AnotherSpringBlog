package com.zhouchaowei.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author wee
 * @date 12/29/15.
 */

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel{

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String role = ROLE_USER;

    @OneToMany(mappedBy = "user")
    private Collection<Post> posts = new ArrayList<Post>();


    public User() {

    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
