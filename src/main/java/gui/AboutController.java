/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import data.Guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class AboutController {

    @FXML private ListView<Guide> guideList;

    @FXML private WebView guideView;

    private WebEngine engine;

    private final ObservableList<Guide> list = FXCollections.observableArrayList();

    @FXML
    void onCloseButtonClicked() {
        Stage stage = (Stage) guideList.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        list.addAll(new Guide("Adding Items", "additems.html"),
                    new Guide("Removing Items", "removeitems.html"),
                    new Guide("Editing Items", "edititems.html"),
                    new Guide("Sorting and Filtering", "sortitems.html"),
                    new Guide("Searching", "searchitems.html"),
                    new Guide("Importing and Exporting", "importexport.html"));
        guideList.fixedCellSizeProperty().set(25);
        guideList.setItems(list);
        engine = guideView.getEngine();

        guideList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> loadPage(newValue.getLocalHtml())));
        guideList.getSelectionModel().select(0);
    }

    public void loadPage(String html) {
        URL url = this.getClass().getResource("guides/html/" + html);
        engine.load(Objects.requireNonNull(url).toString());
    }

}
