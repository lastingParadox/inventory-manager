/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Zander Preston
 */

package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class InventoryManagementApplication extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/main.fxml")));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/main.css")).toExternalForm());
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.getIcons().add(new Image("gui/images/appicon.png"));
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    public static void main(String[] args) {
        Font.loadFont(InventoryManagementApplication.class.getResourceAsStream("fonts/Penumbra-HalfSerif-Std.ttf"), 16);
        Font.loadFont(InventoryManagementApplication.class.getResourceAsStream("fonts/NotoSans-Regular.ttf"), 16);
        launch(args);
    }

}
