package com.nowcoder.toutiao;

import com.nowcoder.toutiao.dao.UserDAO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ToutiaoApplication {


    public static void main(String[] args) {
        SpringApplication.run(ToutiaoApplication.class, args);
    }

}
