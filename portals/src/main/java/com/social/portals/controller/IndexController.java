package com.social.portals.controller;

import com.social.portals.model.Holder;
import com.social.portals.model.Question;
import com.social.portals.model.User;
import com.social.portals.model.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    Holder holder;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<ViewObject> viewObjects = new ArrayList<>();
        //获取最大分页数
        int maxPage = this.restTemplate.getForObject("http://127.0.0.1:8082/question/all", List.class).size();
        maxPage = maxPage % 5 == 0 ? maxPage / 5 : maxPage / 5 + 1;
        //分页获取所有问题
        Question[] questionList = this.restTemplate.getForObject("http://127.0.0.1:8082/question?page=1&count=5", Question[].class);
        //获取模板数据
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            //获取问题的提问者
            User user = this.restTemplate.getForObject("http://127.0.0.1:8081/user/" + question.getUserId(), User.class);
            viewObject.put("question", question);
            viewObject.put("user", user);
            viewObjects.add(viewObject);
        }
        model.addAttribute("viewObjects", viewObjects);
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("maxPage", maxPage);
        return "index";
    }
}
