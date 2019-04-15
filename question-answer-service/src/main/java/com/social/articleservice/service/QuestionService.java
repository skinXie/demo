package com.social.articleservice.service;

import com.social.articleservice.async.Consumer;
import com.social.articleservice.async.QuestionEvent;
import com.social.articleservice.dao.QuestionDao;
import model.questionAnswer.Question;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    Consumer consumer;
    @Autowired
    RestTemplate restTemplate;

    //发布问题,推送消息
    public int askQuestion(String title, String content, String tag, int userId) {
        Question question = new Question();
        question.setQuestionContent(content);
        question.setQuestionDate(new Date());
        question.setQuestionTitle(title);
        question.setUserId(userId);
        question.setVisitTime(0);
        question.setTag(tag);
        questionDao.insertQuestion(question);
        QuestionEvent e = new QuestionEvent();
        //请求关注的用户
        User[] users = this.restTemplate.getForObject("http://127.0.0.1:8081/follow/" + userId, User[].class);
        //请求作者信息
        User user = this.restTemplate.getForObject("http://127.0.0.1:8081/user/" + userId, User.class);
        //设置事件
        e.setFollowUsers(Arrays.asList(users));
        e.setQ(question);
        e.setUser(user);
        //添加事件
        consumer.getQueue().add(e);
        return question.getQuestionId();
    }

    //查询该用户的提问
    public List<Question> getQuestionsByUserId(int userId) {
        List<Question> questions = questionDao.selectQuestionByUserId(userId);
        return questions;
    }

    //分页查询问题
    public List<Question> getQuestiobByPage(int page, int count) {
        List<Question> questions = questionDao.selectQuestionByPage(count * (page - 1), count);
        return questions;
    }

    //查询所有问题
    public List<Question> getAllQuestion() {
        List<Question> questions = questionDao.selectAllQuestion();
        return questions;
    }

    //根据问题的id获取问题
    public Question getQuestionByQuestionId(int id) {
        return questionDao.selectQuestionByQuestionId(id);
    }

    //增加浏览数
    public int addVisitTime(int time, int questionId) {
        return questionDao.updateVisitTime(time, questionId);
    }
}
