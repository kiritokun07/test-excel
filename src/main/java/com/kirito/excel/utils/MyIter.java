package com.kirito.excel.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器
 */
public class MyIter<T> {

    public static void main(String[] args) {
        List<String> aList = new ArrayList<>();
        aList.add("1");
        aList.add("2");
        aList.add("3");
        aList.add("4");
        aList.add("5");
        aList.add("6");
        aList.add("7");
        aList.add("8");
        aList.add("9");
        aList.add("10");
        aList.add("11");

        MyIter<String> myIter = new MyIter<>(aList, 5);
        while (myIter.hasNext()) {
            List<String> batch1 = myIter.nextBatch();
            System.out.println("batch1 = " + batch1);
        }
    }

    private final List<T> dataList;
    private final Integer batch;
    private Integer curse;

    public MyIter(List<T> dataList, Integer batch) {
        this.dataList = dataList;
        this.batch = batch;
        this.curse = 0;
    }

    public boolean hasNext() {
        return curse < dataList.size();
    }

    public List<T> nextBatch() {
        int toIndex = Math.min(dataList.size(), curse + batch);
        List<T> result = dataList.subList(curse, toIndex);
        curse += batch;
        return result;
    }

}
