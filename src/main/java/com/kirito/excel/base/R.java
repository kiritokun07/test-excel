package com.kirito.excel.base;

import com.kirito.excel.enums.ErrCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class R<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    /**
     * 如果0就成功
     */
    public boolean checkSuccess() {
        return Objects.equals(code, 0);
    }

    private static <T> R<T> newRes(Integer code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> R<T> success(T data) {
        return newRes(0, "success", data);
    }

    public static <T> R<T> result(R<?> r, T t) {
        return newRes(r.getCode(), r.getMsg(), t);
    }

    public static <T> R<T> result(R<?> r) {
        return newRes(r.getCode(), r.getMsg(), null);
    }

    public static R<String> success() {
        return newRes(0, "success", "success");
    }

    public static <T> R<T> fail() {
        return newRes(1, "fail", null);
    }

    public static <T> R<T> fail(String msg) {
        return fail(msg, null);
    }

    public static <T> R<T> fail(String msg, T t) {
        return newRes(1, msg, t);
    }

    public static <T> R<T> fail(ErrCodeEnum errCodeEnum) {
        return newRes(errCodeEnum.getCode(), errCodeEnum.getDesc(), null);
    }

    public static <T> R<T> fail(ErrCodeEnum errCodeEnum, String msg) {
        return newRes(errCodeEnum.getCode(), msg, null);
    }

}
