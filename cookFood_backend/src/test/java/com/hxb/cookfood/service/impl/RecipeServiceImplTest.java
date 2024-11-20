package com.hxb.cookfood.service.impl;

import com.hxb.cookfood.entry.po.Recipe;
import com.hxb.cookfood.entry.pojo.Page;
import com.hxb.cookfood.service.IRecipeService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeServiceImplTest {
    @Resource
    private IRecipeService recipeService;


    @Test
    void findByIngredients() {
//        Page page = recipeService.findByIngredients(List.of("鸡蛋", "鸭蛋"), 1, 10);
//        System.out.println(page);
    }
}