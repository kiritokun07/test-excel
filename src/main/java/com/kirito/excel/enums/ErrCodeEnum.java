package com.kirito.excel.enums;

import lombok.Getter;

/**
 * 错误码
 * <p>
 * 如何高效优雅的使用java枚举
 * https://mp.weixin.qq.com/s/CLr5bcxsG7C8v6qSsagEbw
 */
@Getter
public enum ErrCodeEnum {


    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    PARAM_ERROR(2, "参数错误"),
    INFO_NOT_EXIST(3, "信息不存在"),
    DEVELOPING(10, "开发中"),

    ALIPAY_FAIL(101, "支付宝支付接口调用失败"),
    WXPAY_FAIL(102, "微信支付接口调用失败");

    private final int code;
    private final String desc;

    ErrCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
