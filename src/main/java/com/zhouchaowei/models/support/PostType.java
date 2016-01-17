package com.zhouchaowei.models.support;

/**
 * @author wee
 * @date 12/31/15.
 */
public enum PostType {


    PAGE("Page"),
    POST("Post");

    private String name;

    PostType(String name) {
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

