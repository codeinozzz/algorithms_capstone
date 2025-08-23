package com.example.capstoneii.service;

import com.example.capstoneii.utils.datastructures.LinkedList;

public class TextProcessorService {

    private final StopWordService stopWordService;

    public TextProcessorService() {
        this.stopWordService = new StopWordService();
    }

    public LinkedList<String> extractKeywords(String query) {
        LinkedList<String> keywords = new LinkedList<>();

        if (query == null || query.trim().isEmpty()) {
            return keywords;
        }

        String[] words = normalizeText(query).split("\\s+");

        for (String word : words) {
            String cleanWord = cleanWord(word);

            if (!cleanWord.isEmpty() &&
                    !stopWordService.isStopword(cleanWord) &&
                    !keywords.contains(cleanWord) &&
                    cleanWord.length() >= 2) { // Minimum word length

                keywords.add(cleanWord);
            }
        }

        return keywords;
    }


    private String normalizeText(String text) {
        return text.toLowerCase().trim().replaceAll("\\s+", " ");
    }


    private String cleanWord(String word) {
        return word.replaceAll("[^a-záéíóúñü0-9]", "").trim();
    }


    public int countKeywordMatches(String text, LinkedList<String> keywords) {
        if (text == null || keywords.isEmpty()) {
            return 0;
        }

        String normalizedText = normalizeText(text);
        int matches = 0;

        for (String keyword : keywords) {
            String[] words = normalizedText.split("\\s+");
            for (String word : words) {
                if (cleanWord(word).equals(keyword)) {
                    matches++;
                }
            }
        }

        return matches;
    }

    public String extractSnippet(String text, LinkedList<String> keywords, int maxLength) {
        if (text == null || keywords.isEmpty()) {
            return text != null && text.length() > maxLength ?
                    text.substring(0, maxLength - 3) + "..." : text;
        }

        String normalizedText = normalizeText(text);
        String[] words = normalizedText.split("\\s+");

        // Find the first occurrence of any keyword
        int keywordIndex = -1;
        for (int i = 0; i < words.length; i++) {
            String cleanedWord = cleanWord(words[i]);
            for (String keyword : keywords) {
                if (cleanedWord.equals(keyword)) {
                    keywordIndex = i;
                    break;
                }
            }
            if (keywordIndex != -1) break;
        }

        if (keywordIndex == -1) {
            return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
        }

        int startIndex = Math.max(0, keywordIndex - 10);
        int endIndex = Math.min(words.length, keywordIndex + 15);

        StringBuilder snippet = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) snippet.append(" ");
            snippet.append(words[i]);
        }

        String result = snippet.toString();
        if (result.length() > maxLength) {
            result = result.substring(0, maxLength - 3) + "...";
        }

        if (startIndex > 0) {
            result = "..." + result;
        }

        return result;
    }

    public boolean isValidQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }

        LinkedList<String> keywords = extractKeywords(query);
        return !keywords.isEmpty();
    }

    public String getKeywordStatistics(String originalQuery, LinkedList<String> keywords) {
        if (originalQuery == null) {
            return "No query provided";
        }

        String[] originalWords = originalQuery.trim().split("\\s+");
        int originalWordCount = originalWords.length;
        int keywordCount = keywords.size();
        int stopWordsRemoved = originalWordCount - keywordCount;

        return String.format("Original words: %d, Keywords extracted: %d, Stop words removed: %d",
                originalWordCount, keywordCount, stopWordsRemoved);
    }


}
