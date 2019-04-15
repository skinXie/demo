package com.social.articleservice.service;

import com.social.articleservice.async.AnswerEvent;
import com.social.articleservice.async.Consumer;
import com.social.articleservice.dao.AnswerDao;
import model.questionAnswer.Answer;
import model.questionAnswer.Question;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    AnswerDao answerDao;
    @Autowired
    Consumer consumer;
    @Autowired
    RestTemplate restTemplate;

    //添加回答  private int userId;
    //    private int entityId;
    //    private int entityType;(0-问题  1-答案)
    //    private String answerContent;
    public int addAnswer(int entityType, int userId, int entityId, String content) {
        Answer answer = new Answer();
        answer.setEntityId(entityId);
        answer.setEntityType(entityType);
        answer.setUserId(userId);
        answer.setAnswerContent(content);
        answerDao.insertAnswer(answer);
        //entityType == 0回答的是问题
        if (entityType == 0) {
            //获取问题和作者
            Question q = this.restTemplate.getForObject("http://127.0.0.1:8082/question" + answer.getEntityId(), Question.class);
            User user = this.restTemplate.getForObject("http://127.0.0.1:8081/user" + q.getUserId(), User.class);
            //发布事件，
            AnswerEvent e = new AnswerEvent();
            e.setQ(q);
            e.setUser(user);
            consumer.getQueue().add(e);
        }
        return answer.getAnswerId();
    }


    //分页获取回答(10)
    public List<Answer> getAnswer(int entityType, int entityId, int page, int count) {
        List<Answer> answers = answerDao.selectAnswerByPage(entityType, entityId, count * (page - 1), count);
        return answers;
    }

    //根据实体id获取回答
    public List<Answer> getAnswer(int entityType, int entityId) {
        List<Answer> answers = answerDao.selectAllAnswer(entityType, entityId);
        return answers;
    }
}