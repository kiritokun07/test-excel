package com.kirito.excel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * hotel
 * @author kirito
 * @since 2024-06-10 21:26:21
 */
@Getter
@Setter
//@ApiModel(value = "Hotel对象", description = "hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

//    @ApiModelProperty("名称")
    private String name;

    //城市
    private String city;

//    @ApiModelProperty("地址")
    private String address;

//    @ApiModelProperty("价格")
    private BigDecimal price;

//    @ApiModelProperty("分数")
    private Double score;

    //经度
    private String longitude;

    //纬度
    private String latitude;

//    @ApiModelProperty("创建时间")
//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

//    @ApiModelProperty("最近更新时间")
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    @ApiModelProperty("0:正常; -100:删除;")
    private Integer dataStatus;

}
