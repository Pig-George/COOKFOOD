package com.hxb.cookfood.service;

import com.hxb.cookfood.entry.po.Ingredient;
import com.hxb.cookfood.entry.pojo.Page;

import java.util.List;

public interface IIngredientService {
    public Ingredient findById(String id);
    public Ingredient findByName(String name);
    public Page findByDescription(List<String> keys, int page, int size);
}
