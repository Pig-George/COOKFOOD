package com.hxb.cookfood.service.impl;

import com.hxb.cookfood.service.IIngredientService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IngredientServiceTest {

    @Resource
    private IIngredientService ingredientService;
    @Test
    void findByName() {
//        ingredientService.findByName("油").forEach(System.out::println);
    }
}