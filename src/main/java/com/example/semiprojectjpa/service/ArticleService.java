package com.example.semiprojectjpa.service;

import com.example.semiprojectjpa.entity.Article;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ArticleService {

    Iterable<Article> selectAll();

    Optional<Article> selectOneById(Long id);

    void insertArticle(Article article);

    void updateArticle(Article article);

    void deleteArticleById(Long id);

    Page<Article> findAll(int page);

}
