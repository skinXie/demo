package com.social.userservice.controller;

import com.social.userservice.service.FollowService;
import model.portals.Holder;
import model.questionAnswer.Question;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowController {
    @Autowired
    FollowService followService;

    //获取用户关注的用户
    @RequestMapping("/api/user/{id}/following-user")
    public List<User> getFollowUser(@PathVariable("id") int userId) {
        return followService.getFollowUser(userId);
    }

    //获取用户关注的问题
    @RequestMapping("/api/user/{id}/following-question")
    public List<Question> getFollowQuestion(@PathVariable("id") int userId) {
        return followService.getFollowQuestion(userId);
    }

    //用户关注
    @RequestMapping(value = "/api/following-user/{id}", method = RequestMethod.POST)
    public String FollowUser(int userId, @PathVariable("id") int entityId) {
        if (followService.isFollow(userId, entityId, "用户")) {
            followService.addFollow(userId, entityId, "用户");
            return "关注成功";
        }
        followService.cancleFollow(userId, entityId, "用户");
        return "关注取消";
    }

    //问题关注
    @RequestMapping(value = "/api/following-question/{id}", method = RequestMethod.POST)
    public String FollowQuestion(int userId, @PathVariable("id") int entityId) {
        if (followService.isFollow(userId, entityId, "问题")) {
            followService.addFollow(userId, entityId, "问题");
            return "关注成功";
        }
        followService.cancleFollow(userId, entityId, "问题");
        return "关注取消";
    }

    //判断A是否关注B
}
