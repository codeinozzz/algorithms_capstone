package com.example.capstoneii.models;

import java.util.ArrayList;
import java.util.List;

public class Regulation {
    private String name;
    private List<Title> titles;

    public Regulation() {
        this.titles = new ArrayList<>();
        this.name = "GENERAL THESIS REGULATION";
    }

    public Regulation(String name) {
        this.name = name;
        this.titles = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public void addTitle(Title title) {
        this.titles.add(title);
    }

    public void clear() {
        this.titles.clear();
    }

    public Article findArticle(String articleNumber) {
        for (Title title : titles) {
            Article article = title.findArticleByNumber(articleNumber);
            if (article != null) {
                return article;
            }
        }
        return null;
    }

    public Title findTitleByArticle(String articleNumber) {
        for (Title title : titles) {
            if (title.findArticleByNumber(articleNumber) != null) {
                return title;
            }
        }
        return null;
    }

    public int getTotalArticles() {
        return titles.stream()
                .mapToInt(Title::getArticleCount)
                .sum();
    }

    public boolean isEmpty() {
        return titles.isEmpty();
    }

    @Override
    public String toString() {
        return "Regulation{" +
                "name='" + name + '\'' +
                ", titleCount=" + titles.size() +
                ", totalArticles=" + getTotalArticles() +
                '}';
    }
}
