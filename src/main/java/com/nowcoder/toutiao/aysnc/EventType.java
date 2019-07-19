package com.nowcoder.toutiao.aysnc;

public enum EventType {
    LIKE(0),
    DISLIKE(1),
    MAIL(2),
    Message(3),
    LOGIN(4);

    int value;
     EventType(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
