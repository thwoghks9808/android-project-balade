package com.bit.balade.route;

public class BoardData {
    private String user_id;
    private String content;
    private String no ;

    public BoardData(String user_id, String content, String no) {
        this.user_id = user_id;
        this.content = content;
        this.no = no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
