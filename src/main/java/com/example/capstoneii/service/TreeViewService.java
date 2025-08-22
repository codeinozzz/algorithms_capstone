package com.example.capstoneii.service;

import com.example.capstoneii.models.Article;
import com.example.capstoneii.models.Regulation;
import com.example.capstoneii.models.Title;
import javafx.scene.control.TreeItem;

public class TreeViewService {

    public TreeItem<String> buildTreeView(Regulation regulation) {
        TreeItem<String> root = new TreeItem<>(regulation.getName());
        root.setExpanded(true);

        for (Title title : regulation.getTitles()) {
            TreeItem<String> titleItem = createTitleItem(title);
            root.getChildren().add(titleItem);
        }

        return root;
    }

    private TreeItem<String> createTitleItem(Title title) {
        TreeItem<String> titleItem = new TreeItem<>(title.getName());
        titleItem.setExpanded(false); // Closed by default for better navigation

        for (Article article : title.getArticles()) {
            TreeItem<String> articleItem = createArticleItem(article);
            titleItem.getChildren().add(articleItem);
        }

        return titleItem;
    }


    private TreeItem<String> createArticleItem(Article article) {
        // Only show the article number
        return new TreeItem<>("Art " + article.getNumber());
    }

    public ElementType determineElementType(TreeItem<String> item) {
        if (item == null) {
            return ElementType.NONE;
        }

        if (item.getParent() == null) {
            return ElementType.ROOT;
        } else if (item.getChildren().isEmpty()) {
            return ElementType.ARTICLE;
        } else {
            return ElementType.TITLE;
        }
    }

    public String extractArticleNumber(TreeItem<String> item) {
        if (item != null && item.getValue().startsWith("Art ")) {
            return item.getValue().replace("Art ", "");
        }
        return null;
    }

    public enum ElementType {
        ROOT, TITLE, ARTICLE, NONE
    }
}
