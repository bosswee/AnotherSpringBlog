package com.zhouchaowei.models.support;

/**
 * @author wee
 * @date 12/31/15.
 */
public enum PostFormat {

    HTML("Html"),
    MARKDOWN("Markdown");

    private String displayName;

    PostFormat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }



    @Override
    public String toString() {
        return getDisplayName();
    }
}

