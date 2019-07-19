package com.nowcoder.toutiao.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

//    @Autowired
//    private FreeMarker;
//    Engine velocityEngine;

    @Autowired
    Configuration configuration; //freeMarker configuration


    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("牛客中级课");
            InternetAddress from = new InternetAddress(nick + "<1208725256@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            configuration.setDirectoryForTemplateLoading(new File("F:\\AEM6.4\\IDEA_Project\\toutiao\\src\\main\\resources\\templates"));

            Template template1 = configuration.getTemplate(template);

            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);

//            VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, , "UTF-8", model);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("1208725256@qq.com");
        //此处设置的是授权码，不是密码
        mailSender.setPassword("loqzmfknbzcejgdi");
        mailSender.setHost("smtp.exmail.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.timeout", 5000);

        mailSender.setJavaMailProperties(javaMailProperties);
    }
}