package com.example.semiprojectjpa.controller;

import com.example.semiprojectjpa.entity.Article;
import com.example.semiprojectjpa.service.ArticleService;
import com.example.semiprojectjpa.service.MyMemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService service;

    @GetMapping("list")
    public ModelAndView list(ModelAndView mav, Authentication authentication) {
        Page<Article> articleList = service.findAll(0);
        mav.addObject("articleList", articleList);
        MyMemberDetails memberDetails = (MyMemberDetails) authentication.getPrincipal();
        mav.addObject("author", memberDetails.getName());
        mav.addObject("id", memberDetails.getUsername());
        mav.setViewName("list");
        return mav;
    }

    @RequestMapping("/insert")
    public ModelAndView insert(@ModelAttribute Article article, ModelAndView mav, Authentication authentication) {
        mav.addObject("article", article);
        mav.setViewName("insert");
        MyMemberDetails memberDetails = (MyMemberDetails) authentication.getPrincipal();
        mav.addObject("author", memberDetails.getName());
        mav.addObject("username", memberDetails.getUsername());
        return mav;
    }

    @PostMapping("/insert")
    public ModelAndView insertPost(@ModelAttribute @Validated Article article, BindingResult result, ModelAndView mav) {
        if(result.hasErrors()) {
            mav.addObject("message", "입력내용을 확인하세요");
            mav.setViewName("insert");
            return mav;
        }
        service.insertArticle(article);
        mav = new ModelAndView("redirect:/list");
        return mav;
    }

    @RequestMapping("/list/view/{id}")
    public ModelAndView showOne(@ModelAttribute Article article, ModelAndView mav, @PathVariable("id") Long id) {
        Optional<Article> articleOptional = service.selectOneById(id);
        if(articleOptional.isPresent()) {
            article = articleOptional.get();
            mav.addObject("articleOptional", article);
        }
        mav.setViewName("one");
        return mav;
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdate(ModelAndView mav, @PathVariable("id") Long id, Authentication authentication) {
        Optional<Article> articleOptional = service.selectOneById(id);
        MyMemberDetails memberDetails = (MyMemberDetails) authentication.getPrincipal();
        if(articleOptional.isPresent()) {
            Article article = articleOptional.get();
            if(memberDetails.getUsername().equals(article.getUsername())){
                mav.addObject("articleOptional", article);
                mav.addObject("author", memberDetails.getName());
                mav.addObject("username", memberDetails.getUsername());
            } else {
                mav = new ModelAndView("authentication/fail");
                return mav;
            }
        }
        mav.setViewName("update");
        return mav;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute @Validated Article article, BindingResult result, ModelAndView mav) {
        if(result.hasErrors()) {
            mav.addObject("message", "입력내용을 확인하세요");
            mav.setViewName("update");
            return mav;
        }
        service.updateArticle(article);
        mav = new ModelAndView("redirect:/list");
        return mav;
    }

    @PostMapping("/delete")
    public ModelAndView delete (@RequestParam("id") String id, ModelAndView mav, RedirectAttributes redirectAttributes, Authentication authentication) {
        Optional<Article> articleOptional = service.selectOneById(Long.parseLong(id));
        MyMemberDetails memberDetails = (MyMemberDetails) authentication.getPrincipal();
        if(articleOptional.isPresent()) {
            Article article = articleOptional.get();
            if(memberDetails.getUsername().equals(article.getUsername())){
                service.deleteArticleById(Long.parseLong(id));
            } else {
                mav = new ModelAndView("authentication/fail");
                return mav;
            }
        }
        redirectAttributes.addFlashAttribute("decomplete", "삭제완료!");
        mav = new ModelAndView("redirect:/list");
        return mav;
    }
}
