package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.aspect.LogAspect;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.service.ToutiaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String index(HttpSession session) {
        logger.info("Visit Index.");
        return "Hello world, "+session.getAttribute("msg")+toutiaoService.say();
    }

    @RequestMapping("/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") String type,
                          @RequestParam(value = "key", defaultValue = "nowcode") String key) {
        return String.format("GID{%s},UID{%d},Type{%s},Key{%s}", groupId, userId, type, key);
    }

    @RequestMapping("/vm")
    public String news(Model model) {
        model.addAttribute("value1", "vvv1");
        List<String> colors = Arrays.asList(new String[]{"RED", "YELLOW", "GREEN"});
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; ++i) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }

        model.addAttribute("color", colors);
        model.addAttribute("map", map);
//        model.addAttribute("user", new User("Jim"));
        return "aaa";
    }

    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + " : " + request.getHeader(name) + "</br>");
        }

        for (Cookie cookie : request.getCookies()) {
            sb.append("Cookiesaaa : " + cookie.getName() + " : " + cookie.getValue() + "<br>");
        }

        sb.append("getMethod : " + request.getMethod() + "</br>");
        sb.append("getRequestURI : " + request.getRequestURI() + "</br>");
        sb.append("getQueryString : " + request.getQueryString() + "</br>");
        sb.append("getPathInfo : " + request.getPathInfo() + "</br>");


        return sb.toString();
    }

    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderId,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "nowcoderId form cookie : " + nowcoderId;
    }

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                                 HttpSession session) {
        /*RedirectView red = new RedirectView("/", true);
        if (code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;*/
        session.setAttribute("msg", "Jump from redirect.");
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false) String key) throws IllegalAccessException {
        if ("admin".equals(key)){
            return "hello admin.";
        }
        throw new IllegalAccessException("Key 错误");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error : " + e.getMessage();
    }
}
