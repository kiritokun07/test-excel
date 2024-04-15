package com.kirito.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kirito.excel.mapper")
public class TestExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestExcelApplication.class, args);
    }

}
