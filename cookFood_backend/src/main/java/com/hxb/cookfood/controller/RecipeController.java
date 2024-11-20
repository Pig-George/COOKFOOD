package com.hxb.cookfood.controller;

import com.hxb.cookfood.entry.pojo.R;
import com.hxb.cookfood.service.IRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("recipe")
@RequiredArgsConstructor
@Tag(name = "recipe")
public class RecipeController {
    private final IRecipeService recipeService;

    @Operation(summary = "getRecipeById")
    @GetMapping("{id}")
    public R getRecipeById(@PathVariable String id){
        return R.ok(recipeService.findById(id));
    }

    @GetMapping("random/{count}")
    public R getRecipeRandom(@PathVariable Integer count){
        return R.ok(recipeService.findRandom(count));
    }

    @Operation(summary = "getRecipeByKeywords")
    @GetMapping("keyword")
    public R getRecipeByKeywords(@RequestParam List<String> keys, @RequestParam List<String> fields, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        return R.ok(recipeService.findByKeywords(keys, fields, page, size));
    }

    @Operation(summary = "getRecipeByName")
    @GetMapping("name")
    public R getRecipeByName(@RequestParam String name){
        return R.ok(recipeService.findByName(name));
    }

    @Operation(summary = "getRecipePage")
    @GetMapping("list")
    public R getRecipePage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return R.ok(recipeService.findPage(page,size));
    }

    @Operation(summary = "getIngredients")
    @GetMapping("ingredients")
    public R getIngredients(@RequestParam List<String> ingredients, @RequestParam List<String> fields,  @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return R.ok(recipeService.findByIngredients(ingredients, fields, page, size));
    }

}
