package com.kirito.excel.base;

import com.kirito.excel.enums.ErrCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private ErrCodeEnum errCodeEnum;
    private String msg;

    public BusinessException(ErrCodeEnum errCodeEnum) {
        super(errCodeEnum.getDesc());
        this.errCodeEnum = errCodeEnum;
        this.msg = errCodeEnum.getDesc();
    }

    public BusinessException(ErrCodeEnum errCodeEnum, String msg) {
        super(errCodeEnum.getDesc() + ":" + msg);
        this.errCodeEnum = errCodeEnum;
        this.msg = msg;
    }

}
