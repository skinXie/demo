package com.social.portals.controller;

import com.social.portals.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Holder holder;

    @RequestMapping(value = "/question/{qid}")
    public String question(Model model, @PathVariable(value = "qid") int questionId) {

        //根据问题id获取问题
        Question question = this.restTemplate.getForObject("http://127.0.0.1:8082/question/" + questionId, Question.class);
        //获取问题作者
        User user = this.restTemplate.getForObject("http://127.0.0.1:8081/user/" + question.getUserId(), User.class);
        //添加视图类
        List<ViewObject> viewObjects = new ArrayList<>();
        //判断当前用户是否关注问题发布者
        Follow follow = null;
        if (holder.getUser() != null) {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("userA", holder.getUser().getUserId());
            params.add("userB", user.getUserId());
            follow = this.restTemplate.postForObject("http://127.0.0.1:8081/follow/isfollow", params, Follow.class);
        }
        //分页获取对问题的回答,数量
        Answer[] answerToQuestion = this.restTemplate.getForObject("http://127.0.0.1:8082/answer?entityType=0&entityId=" + questionId + "&page=1&count=5", Answer[].class);
        int page = this.restTemplate.getForObject("http://127.0.0.1:8082/answer?entityType=0&entityId=" + questionId, Answer[].class).length;
        page = page % 5 == 0 ? page / 5 : page / 5 + 1;
        //数据放进视图类
        if (answerToQuestion != null) {
            for (Answer answer : answerToQuestion) {
                //根据用户id判断是否点过赞
                Zan zan = null;
                if (holder.getUser() != null) {
                    zan = this.restTemplate.getForObject("http://127.0.0.1:8082/zan?answerId=" + answer.getAnswerId() + " &userId=" + holder.getUser().getUserId(), Zan.class);
                }
                //添加视图类
                List<ViewObject> viewObjects1 = new ArrayList<>();
                //获取对答案的回答
                Answer[] answers = this.restTemplate.getForObject("http://127.0.0.1:8082/answer?entityType=1&entityId=" + answer.getAnswerId(), Answer[].class);
                //获取赞的数量
                int zanCount = this.restTemplate.getForObject("http://127.0.0.1:8082/zan/count?answerId=" + answer.getAnswerId(), Integer.class);
                //获取回答问题的用户
                User user1 = this.restTemplate.getForObject("http://127.0.0.1:8081/user/" + answer.getUserId(), User.class);
                if (answers != null) {
                    //获取回答答案的用户和回复
                    for (Answer answer1 : answers) {
                        ViewObject viewObject = new ViewObject();
                        User user2 = this.restTemplate.getForObject("http://127.0.0.1:8081/user/" + answer1.getUserId(), User.class);
                        viewObject.put("user", user2);
                        viewObject.put("answer", answer1);
                        viewObjects1.add(viewObject);
                    }
                }
                ViewObject viewObject = new ViewObject();
                viewObject.put("zanNote", zan);
                viewObject.put("answer", answer);
                viewObject.put("user", user1);
                viewObject.put("zan", zanCount);
                viewObject.put("viewObjects1", viewObjects1);
                viewObjects.add(viewObject);
            }
        }

        //浏览数+1
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("qid", questionId);
        this.restTemplate.postForObject("http://127.0.0.1:8082/question/visit", params, Integer.class);
        model.addAttribute("question", question);
        model.addAttribute("follow", follow);
        model.addAttribute("user", user);
        model.addAttribute("answerToQuestion", answerToQuestion);
        model.addAttribute("viewObjects", viewObjects);
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("page", page);
        return "question";
    }
}
