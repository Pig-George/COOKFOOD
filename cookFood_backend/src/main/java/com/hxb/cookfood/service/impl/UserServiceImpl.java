package com.hxb.cookfood.service.impl;

import com.hxb.cookfood.constant.CollectionName;
import com.hxb.cookfood.entry.po.User;
import com.hxb.cookfood.service.IUserService;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final MongoTemplate mongoTemplate;
    public Boolean register(String code) {
        User user = new User();
        user.set_id(code);
        user.setName("cf_" + code.substring(0, 4));
        mongoTemplate.insert(user, "user");
        return true;
    }

    @Override
    public Boolean login(String code) {
        Query query = new Query(Criteria.where("_id").is(code));
        if (mongoTemplate.exists(query, CollectionName.USER)) {
            return true;
        }
        return register(code);
    }

    @Override
    public User getUserInfo(String code) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(code)), User.class, CollectionName.USER);
    }

    @Override
    public boolean updateUserName(String code, String name) {
        Query query = new Query(Criteria.where("_id").is(code));
        UpdateResult updated = mongoTemplate.updateFirst(query, new Update().set("name", name), User.class, CollectionName.USER);
        return updated.wasAcknowledged();
    }
}
