package com.example.capstoneii.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representing a regulation title with its articles
 */
public class Title {
    private String name;
    private List<Article> articles;

    public Title() {
        this.articles = new ArrayList<>();
    }

    public Title(String name) {
        this.name = name;
        this.articles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    // Utility methods
    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public Article findArticleByNumber(String number) {
        return articles.stream()
                .filter(article -> article.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }

    public int getArticleCount() {
        return articles.size();
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                ", articleCount=" + articles.size() +
                '}';
    }
}
