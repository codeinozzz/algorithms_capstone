package com.example.capstoneii.models;

public class SearchResultItem {
    private String articleNumber;
    private String articleContent;
    private String titleName;
    private int keywordMatches;
    private int totalKeywords;

    public SearchResultItem(String articleNumber, String articleContent, String titleName) {
        this.articleNumber = articleNumber;
        this.articleContent = articleContent;
        this.titleName = titleName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public String getTitleName() {
        return titleName;
    }

    public int getKeywordMatches() {
        return keywordMatches;
    }

    public void setKeywordMatches(int keywordMatches) {
        this.keywordMatches = keywordMatches;
    }

    public int getTotalKeywords() {
        return totalKeywords;
    }

    public void setTotalKeywords(int totalKeywords) {
        this.totalKeywords = totalKeywords;
    }

    public double getRelevanceScore() {
        return totalKeywords > 0 ? (double) keywordMatches / totalKeywords : 0.0;
    }

    public String getRelevancePercentage() {
        return String.format("%.0f%%", getRelevanceScore() * 100);
    }
}