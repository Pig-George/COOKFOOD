package com.hxb.cookfood.service.impl;

import com.hxb.cookfood.constant.CollectionName;
import com.hxb.cookfood.entry.po.Recipe;
import com.hxb.cookfood.entry.pojo.Page;
import com.hxb.cookfood.service.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {
    private final MongoTemplate mongoTemplate;


    @Override
    public List<Recipe> findPage(int page, int size) {
        Query query = new Query().limit(size).skip((page - 1) * size);
        return mongoTemplate.find(query, Recipe.class);
    }

    @Override
    public Recipe findById(String id) {
        return mongoTemplate.findById(id, Recipe.class);
    }

    @Override
    public List<Recipe> findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Recipe.class);
    }

    @Override
    public Page findByKeywords(List<String> keywords, List<String> fields, int page, int size) {
        return findByStringsWithField("keywords", fields, keywords, page, size);
    }

    @Override
    public Page findByIngredients(List<String> ingredients, List<String> fields, int page, int size) {
        return findByStringsWithField("recipeIngredient", fields, ingredients, page, size);
    }

    @Override
    public List<Recipe> findRandom(Integer count) {
        count = count == null ? 1 : count;
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sample(count)
        );
        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, CollectionName.RECIPE, Recipe.class);
        return results.getMappedResults();
    }

    public Page findByStringsWithField(String field, List<String> fields, List<String> strings, int page, int size) {
        StringBuffer regex = new StringBuffer(".*(?:");
        for (String string : strings) {
            regex.append(string);
            regex.append("|");
        }
        regex.deleteCharAt(regex.length() - 1);
        regex.append(").*");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation
                        .unwind(field),
                Aggregation
                        .match(Criteria.where(field)
                                .regex(regex.toString())),
                Aggregation.group("_id")
                        .first("$$ROOT").as("root")
                        .push(field).as(field),
                Aggregation.addFields()
                        .addFieldWithValue("root." + field, "$" + field)
                        .build(),
                Aggregation.replaceRoot("root"),
                !fields.isEmpty() ? Aggregation.project(fields.toArray(new String[0])) : Aggregation.skip(0),
                Aggregation.skip((page - 1) * size),
                Aggregation.limit(size)
        );
        AggregationResults<Recipe> results = mongoTemplate.aggregate(aggregation, CollectionName.RECIPE, Recipe.class);
        return Page.of(page, size, results.getMappedResults());
    }
}
