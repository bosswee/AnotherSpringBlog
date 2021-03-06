package com.zhouchaowei.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wee
 * @date 12/31/15.
 */

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel implements Comparable<BaseModel>, Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    @Override
    public int compareTo(BaseModel o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;

        return this.getId().equals(((BaseModel) other).getId());
    }


    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }


}


