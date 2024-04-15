package com.kirito.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.kirito.excel.config.MyLocalDateTimeConverter;

import java.util.List;

public class ExportUtil {

    public static <T> void export(String destPath, Class<T> clazz, List<T> dataList) {
        EasyExcel.write(destPath, clazz)
                .sheet()
                .registerConverter(new MyLocalDateTimeConverter())
                // 设置字段宽度为自动调整，不太精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(dataList);
    }

}
