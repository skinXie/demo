package com.social.portals.controller;

import com.social.portals.model.Holder;
import com.social.portals.model.Letter;
import com.social.portals.model.User;
import com.social.portals.model.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LetterController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Holder holder;

    @RequestMapping(value = "/letter")
    public String letter(Model model) {
        //获取用户未读的站内信
        Letter[] letters = restTemplate.getForObject("http://127.0.0.1:8081/letter/" + holder.getUser().getUserId(), Letter[].class);
        List<ViewObject> viewObjects = new ArrayList<>();
        for (Letter letter : letters) {
            ViewObject viewObject = new ViewObject();
            //获取站内信的发送者
            User user = restTemplate.getForObject("http://127.0.0.1:8081/user/" + letter.getSenderId(), User.class);
            viewObject.put("user", user);
            viewObject.put("letter", letter);
            viewObjects.add(viewObject);
        }
        model.addAttribute("viewObjects", viewObjects);
        model.addAttribute("holder", holder.getUser());
        return "letter";
    }
}
