package com.example.capstoneii.service;

import com.example.capstoneii.utils.datastructures.LinkedList;

public class QueryProcessorService {
    private final StopWordService stopWordService;

    public QueryProcessorService() {
        this.stopWordService = new StopWordService();
    }

    /**
     * Processes a search query according to the project specifications:
     * 1. Receives the complete query
     * 2. Separates word by word without considering punctuation
     * 3. Searches each word in the stop words set
     * 4. If not found, adds to specific words list
     * 5. Returns LinkedList with specific words for search
     *
     * @param query The complete search query
     * @return LinkedList<String> containing specific words for search
     */
    public LinkedList<String> processQuery(String query) {
        LinkedList<String> specificWords = new LinkedList<>();

        if (query == null || query.trim().isEmpty()) {
            return specificWords;
        }

        // Step 1: Separate word by word without punctuation marks
        String[] words = cleanAndSplitQuery(query);

        // Step 2: Process each word
        for (String word : words) {
            if (!word.isEmpty()) {
                String normalizedWord = normalizeWord(word);

                // Step 3: Search word in stop words set
                if (!stopWordService.isStopword(normalizedWord)) {
                    // Step 4: If not found, add to specific words list
                    if (!specificWords.contains(normalizedWord)) {
                        specificWords.add(normalizedWord);
                    }
                }
                // If word is a stop word, it's ignored and we proceed to next word
            }
        }

        // Step 5: Return list with specific words (key for file search)
        return specificWords;
    }

    /**
     * Cleans the query and splits it into words
     * Removes punctuation and splits by spaces
     */
    private String[] cleanAndSplitQuery(String query) {
        // Remove punctuation marks and split by spaces
        return query.toLowerCase()
                .replaceAll("[^a-záéíóúñü\\s]", " ") // Keep only letters and spaces
                .replaceAll("\\s+", " ")              // Normalize spaces
                .trim()
                .split("\\s+");                       // Split by spaces
    }

    /**
     * Normalizes a word (lowercase and trim)
     */
    private String normalizeWord(String word) {
        return word.toLowerCase().trim();
    }

    /**
     * Gets the count of specific words extracted
     */
    public int getSpecificWordsCount(LinkedList<String> specificWords) {
        return specificWords.size();
    }

    /**
     * Prints the specific words for debugging (optional)
     */
    public void printSpecificWords(LinkedList<String> specificWords) {
        System.out.println("Specific words extracted:");
        for (String word : specificWords) {
            System.out.println("- " + word);
        }
        System.out.println("Total: " + specificWords.size() + " words");
    }

    /**
     * Validates if the query processing was successful
     */
    public boolean hasValidSpecificWords(LinkedList<String> specificWords) {
        return specificWords != null && !specificWords.isEmpty();
    }
}