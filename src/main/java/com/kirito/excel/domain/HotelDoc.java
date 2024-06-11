package com.kirito.excel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDoc {

    private Long id;
    private String name;
    private String city;
    private String address;
    private BigDecimal price;
    private Double score;
    private String location;
    //排序时的 距离值
    private Object distance;

    public HotelDoc(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.city = hotel.getCity();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();
        this.score = hotel.getScore();
        //注意纬度在前经度在后
        this.location = hotel.getLatitude() + ", " + hotel.getLongitude();
    }

}
