package com.nowcoder.toutiao.aysnc;

import java.util.List;

public interface EventHandler {
    void doHandler(EventModel eventModel);
    List<EventType> getSupportEventTypes();
}
