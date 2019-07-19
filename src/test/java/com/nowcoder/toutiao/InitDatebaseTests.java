package com.nowcoder.toutiao;

import com.nowcoder.toutiao.dao.CommentDAO;
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/toutiao.sql")
public class InitDatebaseTests {

    @Autowired(required = false)
    UserDAO userDAO;

    @Autowired(required = false)
    NewsDAO newsDAO;

    @Autowired(required = false)
    LoginTicketDAO ticketDAO;

    @Autowired(required = false)
    CommentDAO commentDAO;

    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.InsertNews(news);

            for (int j=0;j<3;++j){
                Comment comment = new Comment();
                comment.setUserId(i+1);
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                comment.setStatus(0);
                comment.setCreatedDate(new Date());
                comment.setContent("评论测试 " + String.valueOf(i));
                commentDAO.addComment(comment);
            }

            user.setPassword("newpassword");
            userDAO.updatePassword(user);

            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i + 1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("Ticket%d", i + 1));
            ticketDAO.addTicket(ticket);
            ticketDAO.updateStatus(ticket.getTicket(), 2);
        }

        Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
//        userDAO.deleteById(1);
//        Assert.assertNull(userDAO.selectById(1));

        Assert.assertEquals(1, ticketDAO.selectByTicket("Ticket1").getUserId());
        Assert.assertEquals(2, ticketDAO.selectByTicket("Ticket1").getStatus());

    }

}
