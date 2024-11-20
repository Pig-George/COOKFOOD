package com.hxb.cookfood.entry.po;

import lombok.Data;
import lombok.ToString;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Document(collection = "recipe")
@TypeAlias("recipe")
public class Recipe {
    private String _id;
    private String name;
    private String author;
    private String description;
    private String dish;
    private List<String> keywords;
    private List<String> recipeIngredient;
    private List<String> recipeInstructions;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
