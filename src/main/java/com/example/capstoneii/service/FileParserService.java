package com.example.capstoneii.service;

import com.example.capstoneii.models.Article;
import com.example.capstoneii.models.Title;
import com.example.capstoneii.models.Regulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileParserService {

    public Regulation parseFile(File file) throws IOException {
        Regulation regulation = new Regulation();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Title currentTitle = null;
            Article currentArticle = null;
            StringBuilder articleContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (isNewTitle(line)) {

                    if (currentArticle != null && currentTitle != null) {
                        currentArticle.setContent(processContent(articleContent.toString().trim()));
                        currentTitle.addArticle(currentArticle);
                    }

                    currentTitle = new Title();
                    currentTitle.setName(extractTitleName(line));
                    regulation.addTitle(currentTitle);
                    currentArticle = null;
                    articleContent = new StringBuilder();
                }

                else if (isNewArticle(line)) {

                    if (currentArticle != null && currentTitle != null) {
                        currentArticle.setContent(processContent(articleContent.toString().trim()));
                        currentTitle.addArticle(currentArticle);
                    }

                    currentArticle = new Article();
                    currentArticle.setNumber(extractArticleNumber(line));
                    currentArticle.setName(line);
                    articleContent = new StringBuilder();

                    String lineContent = extractArticleContent(line);
                    if (!lineContent.isEmpty()) {
                        articleContent.append(lineContent);
                    }
                }

                else if (isValidContent(line)) {
                    if (articleContent.length() > 0) {
                        articleContent.append("\n");
                    }
                    articleContent.append(line);
                }
            }

            if (currentArticle != null && currentTitle != null) {
                currentArticle.setContent(processContent(articleContent.toString().trim()));
                currentTitle.addArticle(currentArticle);
            }
        }

        return regulation;
    }


    private boolean isNewTitle(String line) {
        return line.startsWith("nTitulo") || line.startsWith("Titulo");
    }

    private boolean isNewArticle(String line) {
        return line.startsWith("Art ");
    }

    private boolean isValidContent(String line) {
        return !line.isEmpty() && !line.equals("#");
    }

    private String extractTitleName(String line) {
        if (line.contains(" ")) {
            return line.substring(line.indexOf(" ") + 1).replace("#", "").trim();
        }
        return line.replace("#", "").trim();
    }

    private String extractArticleNumber(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length >= 2) {
            return parts[1];
        }
        return "1";
    }

    private String extractArticleContent(String line) {
        String[] parts = line.split("\\s+", 3);
        if (parts.length >= 3) {
            return parts[2];
        }
        return "";
    }

    private String processContent(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        StringBuilder processedContent = new StringBuilder();
        String[] lines = content.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {

                if (line.matches("^[a-z]\\).*")) {
                    processedContent.append("\n• ").append(line.substring(2).trim());
                }

                else if (line.startsWith("-") || line.matches("^\\d+[.)].+")) {
                    processedContent.append("\n• ").append(line.replaceFirst("^[-\\d+.)]\\s*", ""));
                }
                else {
                    if (processedContent.length() > 0) {
                        processedContent.append(" ");
                    }
                    processedContent.append(line);
                }
            }
        }

        return processedContent.toString().trim();
    }
}
