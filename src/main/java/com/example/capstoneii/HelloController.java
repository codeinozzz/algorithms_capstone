package com.example.capstoneii;

import com.example.capstoneii.models.Article;
import com.example.capstoneii.models.Title;
import com.example.capstoneii.models.Regulation;
import com.example.capstoneii.service.RegulationService;
import com.example.capstoneii.service.TreeViewService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TreeView<String> artTreeView;

    @FXML
    private Label Titlelbl;

    @FXML
    private Label artNamelbl;

    @FXML
    private Label artDesclbl;


    private final RegulationService regulationService;

    public HelloController() {
        this.regulationService = new RegulationService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTreeView();
        showInitialState();
    }


    private void configureTreeView() {
        artTreeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleTreeViewSelection(newValue)
        );
    }

    private void showInitialState() {
        updateInformationPanel(
                "GENERAL REGULATION",
                "Navigation",
                "Click 'SUBMIT FILE' to load the regulation and then select an article to view its content"
        );
    }

    @FXML
    void readFile(ActionEvent event) {
        File selectedFile = showFileSelectionDialog();

        if (selectedFile != null) {
            try {
                loadFile(selectedFile);
                updateTreeView();
                showSuccessfulLoadState();
            } catch (IOException e) {
                showLoadError(e.getMessage());
            }
        }
    }

    private File showFileSelectionDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select regulation file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text files", "*.txt")
        );

        Stage stage = (Stage) artTreeView.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    private void loadFile(File file) throws IOException {
        regulationService.loadRegulation(file);
    }

    private void updateTreeView() {
        TreeItem<String> root = regulationService.getTreeViewService()
                .buildTreeView(regulationService.getCurrentRegulation());
        artTreeView.setRoot(root);
    }

    private void showSuccessfulLoadState() {
        updateInformationPanel(
                "FILE LOADED",
                "Regulation",
                "File loaded successfully. Navigate through titles and articles in the left panel.\n\n" +
                        regulationService.getStatistics()
        );
    }

    private void showLoadError(String errorMessage) {
        updateInformationPanel(
                "ERROR",
                "File error",
                "Error reading file: " + errorMessage
        );
        System.err.println("Error reading file: " + errorMessage);
    }

    private void handleTreeViewSelection(TreeItem<String> item) {
        if (item == null) return;

        TreeViewService.ElementType type = regulationService.getTreeViewService()
                .determineElementType(item);

        switch (type) {
            case ARTICLE:
                showArticleDetails(item);
                break;
            case TITLE:
                showTitleDetails(item);
                break;
            case ROOT:
                showRootDetails();
                break;
            default:
                showInitialState();
                break;
        }
    }

    private void showArticleDetails(TreeItem<String> item) {
        String articleNumber = regulationService.getTreeViewService()
                .extractArticleNumber(item);

        if (articleNumber != null) {
            Article article = regulationService.findArticle(articleNumber);
            Title title = regulationService.findTitleByArticle(articleNumber);

            if (article != null && title != null) {
                updateInformationPanel(
                        title.getName(),
                        "Article " + article.getNumber(),
                        article.getContent()
                );
            }
        }
    }

    private void showTitleDetails(TreeItem<String> item) {
        updateInformationPanel(
                item.getValue(),
                "Selected title",
                "Click on an article to view its complete content"
        );
    }

    private void showRootDetails() {
        if (regulationService.hasRegulationLoaded()) {
            updateInformationPanel(
                    "GENERAL REGULATION",
                    "Navigation",
                    "Select a title and then an article to view its content\n\n" +
                            regulationService.getStatistics()
            );
        } else {
            showInitialState();
        }
    }

    private void updateInformationPanel(String title, String article, String description) {
        Titlelbl.setText(title);
        artNamelbl.setText(article);
        artDesclbl.setText(description);
    }
}