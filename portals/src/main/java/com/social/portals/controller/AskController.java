package com.social.portals.controller;

import com.social.portals.model.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AskController {
    @Autowired
    Holder holder;

    @RequestMapping(value = "/ask")
    public String ask(Model model) {
        model.addAttribute("userId", holder.getUser().getUserId());
        return "ask";
    }
}
