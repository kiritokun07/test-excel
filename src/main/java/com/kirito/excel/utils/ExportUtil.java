package com.kirito.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.kirito.excel.config.MyLocalDateTimeConverter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ExportUtil {

    public static <T> void export(String destPath, Class<T> clazz, List<T> dataList) {
        EasyExcel.write(destPath, clazz)
                .sheet()
                .registerConverter(new MyLocalDateTimeConverter())
                // 设置字段宽度为自动调整，不太精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(dataList);
    }

    public static final int MAX_NUM_IN_ONE_SHEET = 100_0000;

    public static <T> void export2(String destPath, Class<T> clazz, Integer total, Integer size,
                                   Runnable startFunc,
                                   BiFunction<Integer, Integer, Collection<T>> exportFunc,
                                   BiConsumer<Integer, Integer> noticeFunc,
                                   Runnable endFunc) {
        startFunc.run();
        try (ExcelWriter excelWriter = EasyExcel.write(destPath, clazz).build()
        ) {
            MyPager myPager = new MyPager(total, size);
            int sheetCount = total / MAX_NUM_IN_ONE_SHEET + 1;
            for (int sheetI = 1; sheetI <= sheetCount; ++sheetI) {
                WriteSheet writeSheet = generateWriteSheet("Sheet" + sheetI);
                int currentRows = 0;
                while (myPager.hasNext()) {
                    Collection<T> nextCollection = myPager.nextCollection(exportFunc, noticeFunc);
                    if (!nextCollection.isEmpty()) {
                        excelWriter.write(nextCollection, writeSheet);
                    }
                    currentRows += nextCollection.size();
                    nextCollection.clear();
                    if (currentRows >= MAX_NUM_IN_ONE_SHEET) {
                        break;
                    }
                }

            }
            //WriteSheet writeSheet = generateWriteSheet("Sheet1");
            //MyPager myPager = new MyPager(total, size);
            //while (myPager.hasNext()) {
            //    Collection<T> nextCollection = myPager.nextCollection(exportFunc, noticeFunc);
            //    if (!nextCollection.isEmpty()) {
            //        excelWriter.write(nextCollection, writeSheet);
            //    }
            //    nextCollection.clear();
            //}
        } finally {
            endFunc.run();
        }
    }

    private static WriteSheet generateWriteSheet(String sheetName) {
        return EasyExcel.writerSheet(sheetName)
                .registerConverter(new MyLocalDateTimeConverter())
                // 设置字段宽度为自动调整，不太精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build();
    }

}
