package com.kirito.excel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageQo implements Serializable {

    private Integer index;

    private Integer size;

}
