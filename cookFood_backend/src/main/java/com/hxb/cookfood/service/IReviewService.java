package com.hxb.cookfood.service;

import com.hxb.cookfood.entry.po.Review;
import com.hxb.cookfood.entry.pojo.Page;

public interface IReviewService {
    public Review addReview(String recipeId, String userId, String content);
    public boolean deleteReview(String id);
    public Page findListByRecipeId(String recipeId, int page, int size);
}
