package com.zhouchaowei.models.support;

/**
 * @author wee
 * @date 12/31/15.
 */
public enum PostStatus {

    DRAFT("Draft"),
    PUBLISHED("Published");

    private String name;

    PostStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return getName();
    }
}



