package com.kirito.excel.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 大量数据
 * </p>
 *
 * @author kirito
 * @since 2024-04-15 20:25:10
 */
@Getter
@Setter
@TableName("big_data")
//@ApiModel(value = "BigData对象", description = "大量数据")
public class BigData implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty("名称")
    private String name;

    //@ApiModelProperty("年龄")
    private Integer age;

    //@ApiModelProperty("描述")
    private String detail;

    //@ApiModelProperty("创建时间")
//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
