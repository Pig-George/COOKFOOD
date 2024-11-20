package com.hxb.cookfood.service;

import com.hxb.cookfood.entry.po.User;

public interface IUserService {
     public Boolean login(String code);
     public User getUserInfo(String code);

     boolean updateUserName(String code, String name);
}
