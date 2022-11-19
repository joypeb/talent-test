package com.children.talent.controller;

import com.children.talent.domain.Talent;
import com.children.talent.domain.dto.TalentDto;
import com.children.talent.service.TalentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/talent")
@Slf4j
public class TalentController {

    private final TalentService talentService;

    @Autowired
    public TalentController(TalentService talentService) {
        this.talentService = talentService;
    }

    @GetMapping()
    public String index() {
        return "redirect:/talent/list/name";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addRegistration(TalentDto talentDto, Model model) {
        log.info("registraion controller : " + talentDto.getName() + ", " + talentDto.getNums());
        String[] result = talentService.addRegistraion(talentDto.getName(),talentDto.getNums());

        if(result[1].equals("")) {
            return "redirect:/talent/"+result[0]+"/name";
        } else{
            model.addAttribute("errorMsg",result[1]);
            return result[0];
        }
    }

    @GetMapping("/list/article")
    public String listArticle(/*@RequestParam(required = false,defaultValue = "1")int num*/ Model model) {
        List<Talent> talents = talentService.listArticle();
        model.addAttribute("talents", talents);
        return "articleList";
    }

    @GetMapping("/list/name")
    public String listName(/*@RequestParam(required = false,defaultValue = "1")int num*/ Model model) {
        List<Talent> talents = talentService.listName();
        model.addAttribute("talents", talents);
        return "nameList";
    }

    @GetMapping("/list/all")
    public String listAll(/*@RequestParam(required = false,defaultValue = "1")int num*/ Model model) {
        List<Talent> talents = talentService.listAll();
        model.addAttribute("talents", talents);
        return "nameAll";
    }

    @GetMapping("/name/{name}")
    public String selectName(@PathVariable String name, Model model) {
        List<Talent> talents = talentService.selectName(name);
        model.addAttribute("name",name);
        model.addAttribute("talents",talents);

        return "selectName";
    }
}
