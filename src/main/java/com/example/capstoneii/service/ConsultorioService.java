package com.example.capstoneii.service;

import com.example.capstoneii.models.Article;
import com.example.capstoneii.models.Regulation;
import com.example.capstoneii.models.Title;
import com.example.capstoneii.models.SearchResultItem;
import com.example.capstoneii.utils.datastructures.HashMap;
import com.example.capstoneii.utils.datastructures.LinkedList;
import com.example.capstoneii.utils.datastructures.AVLTree;

public class ConsultorioService {

    private final StopWordService stopWordService;
    private final AVLTree<String, LinkedList<String>> wordIndex;
    private final HashMap<String, Article> articlesMap;
    private final HashMap<String, Title> articleTitleMap;
    private Regulation currentRegulation;

    public ConsultorioService() {
        this.stopWordService = new StopWordService();
        this.wordIndex = new AVLTree<>();
        this.articlesMap = new HashMap<>();
        this.articleTitleMap = new HashMap<>();
    }

    public void loadRegulation(Regulation regulation) {
        this.currentRegulation = regulation;
        wordIndex.clear();

        for (Title title : regulation.getTitles()) {
            for (Article article : title.getArticles()) {
                articlesMap.add(article.getNumber(), article);
                articleTitleMap.add(article.getNumber(), title);
                extractWordsFromArticle(article);
            }
        }
    }


    public LinkedList<String> processQuery(String query) {
        LinkedList<String> specificWords = new LinkedList<>();

        if (query == null || query.trim().isEmpty()) {
            return specificWords;
        }

        String[] words = query.toLowerCase()
                .replaceAll("[^a-záéíóúñü\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .split("\\s+");

        for (String word : words) {
            if (!word.isEmpty() && !stopWordService.isStopword(word)) {
                if (!specificWords.contains(word)) {
                    specificWords.add(word);
                }
            }
        }

        return specificWords;
    }

    // Search method - receives specific words list
    public LinkedList<SearchResultItem> search(LinkedList<String> specificWords) {
        LinkedList<SearchResultItem> results = new LinkedList<>();

        if (specificWords.isEmpty()) {
            return results;
        }

        HashMap<String, Integer> matchCounter = new HashMap<>();

        for (String word : specificWords) {
            LinkedList<String> articlesWithWord = wordIndex.search(word);
            if (articlesWithWord != null) {
                for (String articleNumber : articlesWithWord) {
                    Integer count = matchCounter.get(articleNumber);
                    matchCounter.add(articleNumber, count == null ? 1 : count + 1);
                }
            }
        }

        int requiredWords = Math.max(1, specificWords.size() / 2);

        for (Title title : currentRegulation.getTitles()) {
            for (Article article : title.getArticles()) {
                String articleNumber = article.getNumber();
                Integer matches = matchCounter.get(articleNumber);

                if (matches != null && matches >= requiredWords) {
                    SearchResultItem result = new SearchResultItem(
                            article.getNumber(), article.getContent(), title.getName());
                    result.setKeywordMatches(matches);
                    result.setTotalKeywords(specificWords.size());
                    results.add(result);
                }
            }
        }

        return results;
    }

    // Show articles method
    public void showArticles(LinkedList<SearchResultItem> results) {
        if (results.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }

        for (SearchResultItem result : results) {
            System.out.println("Art " + result.getArticleNumber() + " - " +
                    result.getTitleName() + " (" + result.getRelevancePercentage() + ")");
        }
    }

    // Main public method that integrates the complete flow
    public LinkedList<SearchResultItem> performQuery(String query) {
        LinkedList<String> specificWords = processQuery(query);
        return search(specificWords);
    }

    private void extractWordsFromArticle(Article article) {
        String[] words = article.getContent().toLowerCase()
                .replaceAll("[^a-záéíóúñü\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .split("\\s+");

        for (String word : words) {
            if (!stopWordService.isStopword(word) && word.length() >= 2) {
                addWordToIndex(word, article.getNumber());
            }
        }
    }

    private void addWordToIndex(String word, String articleNumber) {
        LinkedList<String> articleList = wordIndex.search(word);

        if (articleList == null) {
            articleList = new LinkedList<>();
            articleList.add(articleNumber);
            wordIndex.insert(word, articleList);
        } else {
            if (!articleList.contains(articleNumber)) {
                articleList.add(articleNumber);
            }
        }
    }


}