package com.kirito.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BigDataDto implements Serializable {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("描述")
    private String detail;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
