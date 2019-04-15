package com.social.portals.controller;

import com.social.portals.model.Holder;
import com.social.portals.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class FollowController {
    @Autowired
    Holder holder;
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/follow")
    public String follow(Model model) {
        //获取用户的关注列表
        User[] followeds = this.restTemplate.getForObject("http://127.0.0.1:8081/follow/" + holder.getUser().getUserId(), User[].class);
        model.addAttribute("followeds", followeds);
        model.addAttribute("holder", holder.getUser());
        return "follow";
    }
}
