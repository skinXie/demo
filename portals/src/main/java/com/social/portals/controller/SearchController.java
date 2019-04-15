package com.social.portals.controller;

import com.social.portals.model.Holder;
import com.social.portals.model.QuestionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class SearchController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Holder holder;

    @GetMapping("/search")
    public String search(Model model, @RequestParam("keywords") String keywords,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "count", required = false, defaultValue = "5") int count) {
        QuestionSearch[] questionSearches = this.restTemplate.getForObject("http://127.0.0.1:8083/search/question?keywords=" + keywords + "&page=" + page + "&count=" + count, QuestionSearch[].class);
        model.addAttribute("questionSearches", Arrays.asList(questionSearches));
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("keywords", keywords);
        return "search";
    }
}
