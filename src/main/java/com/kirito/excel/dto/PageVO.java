package com.kirito.excel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageVO<T> implements Serializable {

    private Long total;

    private List<T> list;

    public static <T> PageVO<T> ok(Long total, List<T> list) {
        PageVO<T> result = new PageVO<>();
        result.setTotal(total);
        result.setList(list);
        return result;
    }

    public static <T> PageVO<T> empty() {
        PageVO<T> result = new PageVO<>();
        result.setTotal(0L);
        result.setList(Collections.emptyList());
        return result;
    }

}
