package com.nowcoder.toutiao.aysnc.handler;

import com.nowcoder.toutiao.aysnc.EventHandler;
import com.nowcoder.toutiao.aysnc.EventModel;
import com.nowcoder.toutiao.aysnc.EventType;
import com.nowcoder.toutiao.model.Message;
import com.nowcoder.toutiao.service.MessageService;
import com.nowcoder.toutiao.utils.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;


    @Override
    public void doHandler(EventModel model) {
        // 判断是否有异常登陆 还没写
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆ip异常");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        message.setConversationId("3_"+model.getActorId());
        messageService.addMessage(message);


//        Map<String, Object> map = new HashMap<String, Object>();
////        map.put("username", model.getExt("username"));
////        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆异常", "mail.ftl",
////                map);
    }
//    mails/welcome
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
