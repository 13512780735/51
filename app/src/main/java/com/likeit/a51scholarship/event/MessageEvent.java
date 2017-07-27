package com.likeit.a51scholarship.event;

public class MessageEvent {

    private String type;

    public MessageEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
