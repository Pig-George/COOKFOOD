package com.hxb.cookfood.entry.vo;

import com.hxb.cookfood.entry.po.Review;
import com.hxb.cookfood.entry.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewVo extends Review {
    private Object user;
}
