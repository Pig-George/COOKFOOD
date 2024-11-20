package com.hxb.cookfood.controller;

import com.hxb.cookfood.entry.pojo.R;
import com.hxb.cookfood.service.IIngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("ingredient")
@Tag(name = "ingredient")
public class IngredientController {
    private final IIngredientService ingredientService;

    @Operation(summary = "getIngredientByName")
    @GetMapping("name")
    public R getIngredientByName(@RequestParam String name) {
        return R.ok(ingredientService.findByName(name));
    }

    @Operation(summary = "getIngredientById")
    @GetMapping("{id}")
    public R getIngredientById(@PathVariable String id) {
        return R.ok(ingredientService.findById(id));
    }

    @Operation(summary = "getIngredientByDescription")
    @GetMapping("description")
    public R getIngredientByDescription(@RequestParam List<String> keys, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return R.ok(ingredientService.findByDescription(keys, page, size));
    }
}
