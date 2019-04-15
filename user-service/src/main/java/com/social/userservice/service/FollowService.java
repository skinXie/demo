package com.social.userservice.service;

import com.social.userservice.async.Consumer;
import com.social.userservice.async.FollowEvent;
import com.social.userservice.dao.FollowDao;
import com.social.userservice.dao.UserDao;
import model.questionAnswer.Question;
import model.user.Follow;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    FollowDao followDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Consumer consumer;

    //查询用户关注的用户
    public List<User> getFollowUser(int userId) {
        List<Follow> follows = followDao.selectFollowedId(userId, "用户");
        List<User> items = new ArrayList<>();
        for (Follow follow : follows) {
            User user = userDao.selectUserById(follow.getEntityId());
            items.add(user);
        }
        return items;
    }

    //查询用户关注的问题
    public List<Question> getFollowQuestion(int userId) {
        List<Follow> follows = followDao.selectFollowedId(userId, "问题");
        List<Question> items = new ArrayList<>();
        for (Follow follow : follows) {
            Question q = this.restTemplate.getForObject("http://127.0.0.1:8082/question/" + follow.getEntityId(), Question.class);
            items.add(q);
        }
        return items;
    }

    //添加关注
    public boolean addFollow(int userId, int entityId, String type) {
        return followDao.insertGuanzhu(userId, entityId, type);
    }

    //取消关注
    public boolean cancleFollow(int userId, int entityId, String type) {
        return followDao.deleteGuanzhu(userId, entityId, type);
    }

    //判断是否存在关注
    public boolean isFollow(int userAId, int userBId, String type) {
        return followDao.isFollow(userAId, userBId, type) == null ? false : true;
    }
}
