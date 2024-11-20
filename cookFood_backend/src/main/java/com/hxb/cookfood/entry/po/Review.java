package com.hxb.cookfood.entry.po;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;

@Data
@TypeAlias("review")
public class Review {
    private String _id;
    private String recipeId;
    private String userId;
    private String content;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
