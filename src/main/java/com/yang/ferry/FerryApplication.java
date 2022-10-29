package com.yang.ferry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = "com.yang.ferry.model.dao")
@EnableAspectJAutoProxy(exposeProxy = true)
public class FerryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FerryApplication.class, args);
    }

}
