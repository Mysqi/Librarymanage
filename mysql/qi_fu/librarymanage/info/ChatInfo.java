package com.mysql.qi_fu.librarymanage.info;

import java.io.Serializable;

/**
 * Created by qi_fu on 2017/2/10.
 */

public class ChatInfo implements Serializable{
    private String title;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
