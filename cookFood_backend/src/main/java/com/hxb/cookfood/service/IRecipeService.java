package com.hxb.cookfood.service;

import com.hxb.cookfood.entry.po.Recipe;
import com.hxb.cookfood.entry.pojo.Page;

import java.util.List;

public interface IRecipeService {
    public List<Recipe> findPage(int page, int size);
    public Recipe findById(String id);
    public List<Recipe> findByName(String name);
    public Page findByKeywords(List<String> keywords, List<String> fields, int page, int size);
    public Page findByIngredients(List<String> ingredients, List<String> fields, int page, int size);

    public List<Recipe> findRandom(Integer count);
}
