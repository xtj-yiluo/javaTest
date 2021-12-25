package com.javatest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xtj
 */
@MapperScan("cn.mybatisPlus.test.mapper")
@SpringBootApplication
public class JavaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTestApplication.class, args);
    }

}
