package com.hxb.cookfood;

import com.hxb.cookfood.entry.po.Recipe;
import jakarta.annotation.Resource;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootTest
class CookFoodApplicationTests {
    @Resource
    private MongoTemplate mongoTemplate;

    @Test
    void mainTest() {
        Query query = new Query(Criteria.where("name").is("红烧滩羊肉"));
        List<Recipe> recipes = mongoTemplate.find(query, Recipe.class);
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }

}
