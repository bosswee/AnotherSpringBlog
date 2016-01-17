package com.zhouchaowei.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wee
 * @date 12/29/15.
 */

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag extends BaseModel{


    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    private List<Post> posts = new ArrayList<Post>();

    public Tag() {

    }

    public Tag(String name) {
        this.setName(name);
    }
}