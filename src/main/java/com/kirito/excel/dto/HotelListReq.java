package com.kirito.excel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelListReq implements Serializable {

    private String key;
    private Integer page;
    private Integer size;
    private String sortBy;

    private String city;
    private Integer minPrice;
    private Integer maxPrice;

    //我当前的地理坐标
    private String location;

}
