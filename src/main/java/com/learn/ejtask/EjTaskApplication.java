package com.learn.ejtask;

import com.learn.ejtask.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@MapperScan("com.learn.ejtask.mapper")
@SpringBootApplication()
public class EjTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(EjTaskApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }

}
