package com.example.semiprojectjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.semiprojectjpa.entity.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
}
