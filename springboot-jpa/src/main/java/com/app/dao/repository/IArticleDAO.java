package com.app.dao.repository;

import com.app.dao.entity.Article;

import java.util.List;

/**
 * IArticleDAO
 */
public interface IArticleDAO {
    List<Article> getAllArticles();

    Article getArticleById(int articleId);

    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(int articleId);

    boolean articleExists(String title, String category);
}
