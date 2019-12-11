package com.xh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaohe
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xh.dao")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
