package com.nowcoder.toutiao.aysnc.handler;

import com.nowcoder.toutiao.aysnc.EventHandler;
import com.nowcoder.toutiao.aysnc.EventModel;
import com.nowcoder.toutiao.aysnc.EventType;
import com.nowcoder.toutiao.model.Message;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.service.MessageService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Component
public class LikeEventHandler implements EventHandler {
    @Autowired
    NewsService newsService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel eventModel) {
        newsService.updateLikeCount(Integer.parseInt(eventModel.getExt("newsId")),Integer.parseInt(eventModel.getExt("likeCount")));
        Message message=new Message();
        User user=userService.getUserById(eventModel.getActorId());
        message.setFromId(eventModel.getActorId());
        message.setToId(eventModel.getEntityOwnerId());
        message.setContent("用户"+user.getName()+"赞了你的资讯 "+"http://127.0.0.1:8080:news/"+eventModel.getEntityId());
        message.setCreatedDate(new Date());
        message.setConversationId(eventModel.getActorId() < eventModel.getEntityOwnerId() ? String.format("%d_%d", eventModel.getActorId(), eventModel.getEntityOwnerId()) :
                String.format("%d_%d", eventModel.getActorId(), eventModel.getEntityOwnerId()));
        //System.out.println(message.getFromId()+" "+message.getToId()+" "+message.getContent());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }

}
