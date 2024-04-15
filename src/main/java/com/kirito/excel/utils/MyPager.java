package com.kirito.excel.utils;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * 分页助手
 */
public class MyPager {

    /**
     * 总数
     */
    private Integer total;

    private Integer size;

    private Integer cursor;

    public MyPager(Integer total, Integer size) {
        this.total = total;
        this.size = size;
        this.cursor = 0;
    }

    public boolean hasNext() {
        return cursor < total;
    }

    public void next(BiConsumer<Integer, Integer> func) {
        func.accept(cursor, size);
        cursor += size;
    }

    public <T> Collection<T> nextCollection(BiFunction<Integer, Integer, Collection<T>> func,
                                            BiConsumer<Integer, Integer> noticeFunc) {
        noticeFunc.accept(cursor, total);
        Collection<T> collection = func.apply(cursor, size);
        cursor += size;
        return collection;
    }

    public static void main(String[] args) {
        MyPager myPager = new MyPager(9001, 3000);
        //limit 0,3000 0~2999
        //limit 3000,3000 3000~5999
        //limit 6000,3000 6000~8999
        //limit 9000,3000 9000~9000

        while (myPager.hasNext()) {
            myPager.next((offset, count) -> {
                System.out.println("offset=" + offset + ",count=" + count);
            });
        }
    }

}
