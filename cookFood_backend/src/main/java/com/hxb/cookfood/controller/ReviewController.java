package com.hxb.cookfood.controller;

import com.hxb.cookfood.constant.HttpHeaderParams;
import com.hxb.cookfood.entry.pojo.Page;
import com.hxb.cookfood.entry.pojo.R;
import com.hxb.cookfood.service.IReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
@Tag(name = "review")
public class ReviewController {
    private final IReviewService reviewService;
    @Operation(summary = "findListByRecipeId")
    @GetMapping("{recipeId}")
    public R findListByRecipeId(@PathVariable String recipeId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return R.ok(reviewService.findListByRecipeId(recipeId, page, size));
    }

    @Operation(summary = "addReview")
    @PostMapping
    public R addReview(@RequestParam String recipeId, @RequestParam String content, @RequestHeader(name = HttpHeaderParams.FINGERPRINT) String userId) {
        reviewService.addReview(recipeId, userId, content);
        return R.ok(null);
    }

    @Operation(summary = "deleteReview")
    @DeleteMapping("{id}")
    public R deleteReview(@PathVariable String id) {
        if (reviewService.deleteReview(id)) {
            return R.ok(null);
        }
        return R.error("删除失败");
    }

}
