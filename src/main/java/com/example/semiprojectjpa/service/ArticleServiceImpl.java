package com.example.semiprojectjpa.service;


import com.example.semiprojectjpa.entity.Article;
import com.example.semiprojectjpa.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    // Repository 주입
    @Autowired
    ArticleRepository repository;

    @Override
    public Iterable<Article> selectAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Article> selectOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void insertArticle(Article article) {
        repository.saveAndFlush(article);
    }

    @Override
    public void updateArticle(Article article) {
        repository.save(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Article> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by("id").descending());
        return repository.findAll(pageRequest);
    }
}
