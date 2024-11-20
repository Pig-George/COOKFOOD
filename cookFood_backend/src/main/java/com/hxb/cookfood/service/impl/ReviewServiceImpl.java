package com.hxb.cookfood.service.impl;

import com.hxb.cookfood.constant.CollectionName;
import com.hxb.cookfood.entry.po.Review;
import com.hxb.cookfood.entry.pojo.Page;
import com.hxb.cookfood.entry.vo.ReviewVo;
import com.hxb.cookfood.service.IReviewService;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final MongoTemplate mongoTemplate;
    @Override
    public Review addReview(String recipeId, String userId, String content) {
        Review review = new Review();
        review.setRecipeId(recipeId);
        review.setUserId(userId);
        review.setContent(content);
        return mongoTemplate.insert(review, CollectionName.REVIEW);
    }

    @Override
    public boolean deleteReview(String id) {
        DeleteResult removed = mongoTemplate.remove(id, CollectionName.REVIEW);
        return removed.wasAcknowledged();
    }

    @Override
    public Page findListByRecipeId(String recipeId, int page, int size) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("recipeId").is(recipeId)),
                Aggregation.sort(Sort.Direction.DESC, "createTime"),
                Aggregation.lookup("user", "userId", "_id", "user"),
                Aggregation.skip((page - 1) * size),
                Aggregation.limit(size),
                Aggregation.addFields().addField("user").withValueOfExpression("arrayElemAt('$user', 0)").build()
        );
        AggregationResults<ReviewVo> results = mongoTemplate.aggregate(aggregation, CollectionName.REVIEW, ReviewVo.class);
        return Page.of(page, size, results.getMappedResults());
    }
}
