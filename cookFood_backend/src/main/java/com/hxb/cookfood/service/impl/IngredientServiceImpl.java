package com.hxb.cookfood.service.impl;

import com.hxb.cookfood.constant.CollectionName;
import com.hxb.cookfood.entry.po.Ingredient;
import com.hxb.cookfood.entry.pojo.Page;
import com.hxb.cookfood.service.IIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IIngredientService {
    private final MongoTemplate mongoTemplate;

    @Override
    public Ingredient findById(String id) {
        return mongoTemplate.findById(id, Ingredient.class);
    }

    @Override
    public Ingredient findByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").regex(".*" + name + ".*")), Ingredient.class);
    }

    @Override
    public Page findByDescription(List<String> keys, int page, int size) {
        StringBuffer description = new StringBuffer();
        keys.forEach(v -> description.append(v).append("|"));
        description.deleteCharAt(description.length() - 1);
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("description").regex(".*" + description + ".*")),
            Aggregation.skip((page - 1) * size),
            Aggregation.limit(size)
        );
        AggregationResults<Ingredient> results = mongoTemplate.aggregate(aggregation, CollectionName.INGREDIENT, Ingredient.class);
        return Page.of(page, size, results.getMappedResults());

    }


}
