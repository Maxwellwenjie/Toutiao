package com.nowcoder.toutiao.aysnc.handler;

import com.nowcoder.toutiao.aysnc.EventHandler;
import com.nowcoder.toutiao.aysnc.EventModel;
import com.nowcoder.toutiao.aysnc.EventType;
import com.nowcoder.toutiao.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class MessageEventHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Override
    public void doHandler(EventModel eventModel) {
        messageService.UpdateReadCount(eventModel.getActorId());
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.Message);
    }
}
