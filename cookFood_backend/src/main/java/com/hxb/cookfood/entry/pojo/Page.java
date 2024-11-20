package com.hxb.cookfood.entry.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Page {
    private int page;
    private int size;
    private int total;
    private Object dataList;

    public static Page of(int page, int size, Collection dataList) {
        return new Page(page, size, dataList.size(), dataList);
    }

}
