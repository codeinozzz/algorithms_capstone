package com.example.capstoneii.service;

import com.example.capstoneii.models.Article;
import com.example.capstoneii.models.Regulation;
import com.example.capstoneii.models.Title;

import java.io.File;
import java.io.IOException;

public class RegulationService {
    private final FileParserService fileParserService;
    private final TreeViewService treeViewService;
    private Regulation currentRegulation;

    public RegulationService() {
        this.fileParserService = new FileParserService();
        this.treeViewService = new TreeViewService();
        this.currentRegulation = new Regulation();
    }

    public void loadRegulation(File file) throws IOException {
        this.currentRegulation = fileParserService.parseFile(file);
    }

    public Article findArticle(String articleNumber) {
        return currentRegulation.findArticle(articleNumber);
    }

    public Title findTitleByArticle(String articleNumber) {
        return currentRegulation.findTitleByArticle(articleNumber);
    }

    public Regulation getCurrentRegulation() {
        return currentRegulation;
    }

    public boolean hasRegulationLoaded() {
        return !currentRegulation.isEmpty();
    }

    public String getStatistics() {
        if (currentRegulation.isEmpty()) {
            return "No regulation loaded";
        }

        return String.format("Regulation: %s\nTitles: %d\nArticles: %d",
                currentRegulation.getName(),
                currentRegulation.getTitles().size(),
                currentRegulation.getTotalArticles());
    }

    public void clearRegulation() {
        this.currentRegulation = new Regulation();
    }

    public TreeViewService getTreeViewService() {
        return treeViewService;
    }
}
