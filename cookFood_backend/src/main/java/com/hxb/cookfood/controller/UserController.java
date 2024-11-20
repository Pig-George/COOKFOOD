package com.hxb.cookfood.controller;

import com.hxb.cookfood.constant.HttpHeaderParams;
import com.hxb.cookfood.entry.pojo.R;
import com.hxb.cookfood.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {
    private final IUserService userService;
    @Operation(summary = "login")
    @PostMapping
    public R login(@RequestHeader(name = HttpHeaderParams.FINGERPRINT) String code) {
        System.out.println(code);
        if (userService.login(code)) {
            return R.ok(null);
        }
        return R.error("登录失败");
    }

    @Operation(summary = "getUserInfo")
    @GetMapping
    public R getUserInfo(@RequestHeader(name = HttpHeaderParams.FINGERPRINT) String code) {
        return R.ok(userService.getUserInfo(code));
    }

    @Operation(summary = "updateUserName")
    @PutMapping
    public R updateUserName(@RequestHeader(name = HttpHeaderParams.FINGERPRINT) String code, @RequestParam String name) {
        if (userService.updateUserName(code, name)) {
            return R.ok(null);
        }
        return R.error("更新失败");
    }
}
