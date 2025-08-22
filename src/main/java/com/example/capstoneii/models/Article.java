package com.example.capstoneii.models;


public class Article {
    private String number;
    private String name;
    private String content;

    public Article() {}

    public Article(String number, String name, String content) {
        this.number = number;
        this.name = name;
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}