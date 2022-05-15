package com.example.semiprojectjpa;

import com.example.semiprojectjpa.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.semiprojectjpa.service.ArticleService;

@SpringBootApplication
public class SemiProjectJpaApplication {


    public static void main(String[] args) {
        SpringApplication.run(SemiProjectJpaApplication.class, args).getBean(SemiProjectJpaApplication.class).execute();
    }


    @Autowired
    ArticleService service;

    private void execute() {




    }

    private void showList() {
        System.out.println("전체검색");
        Iterable<Article> articles = service.selectAll();
        for (Article article: articles) {
            System.out.println(article);
        }
        System.out.println("검색종료");
    }


}
