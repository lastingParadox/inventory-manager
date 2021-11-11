package gui;

import data.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class InventoryManagementApplicationController {

    @FXML
    private Label dateLabel;

    @FXML private VBox homeView;

    @FXML private ListView<?> itemList;

    @FXML private TableView<?> itemTable;

    @FXML private VBox itemsView;

    @FXML private TableColumn<?, ?> nameColumn;

    @FXML private TextField searchField;

    @FXML private TableColumn<Item, Boolean> selectedColumn;

    @FXML private TableColumn<?, ?> serialColumn;

    @FXML private Label timeLabel;

    @FXML private Label userLabel;

    @FXML private TableColumn<?, ?> valueColumn;

    private CheckBox selectAllBox = new CheckBox();

    //Create new private ItemList inventory

    @FXML
    void addItemButtonClicked(ActionEvent event) {

    }

    @FXML
    void clearItemButtonClicked(ActionEvent event) {

    }

    @FXML
    void clearSearchButtonClicked(ActionEvent event) {

    }

    @FXML
    void exportHTMLButtonClicked(ActionEvent event) {

    }

    @FXML
    void exportJSONButtonClicked(ActionEvent event) {

    }

    @FXML
    void exportTextButtonClicked(ActionEvent event) {

    }

    @FXML
    void deleteItemButtonClicked(ActionEvent event) {

    }

    @FXML
    void homeButtonClicked(ActionEvent event) {
        //When the home button is clicked, sets the home view to be visible and the item view to not be visible
        //Code only exists to show off both menus in initial design
        homeView.setVisible(true);
        itemsView.setVisible(false);
    }

    @FXML
    void importButtonClicked(ActionEvent event) {

    }

    @FXML
    void itemsButtonClicked(ActionEvent event) {
        //When the items button is clicked, sets the items view to be visible and the home view to not be visible
        //Also sets the selectAll graphic to exist
        //Code only exists to show off both menus in initial design
        itemsView.setVisible(true);
        homeView.setVisible(false);
        selectAllBox.setUserData(selectedColumn);
        selectedColumn.setGraphic(selectAllBox);
    }

    @FXML
    void sortNameButtonClicked(ActionEvent event) {

    }

    @FXML
    void sortSerialButtonClicked(ActionEvent event) {

    }

    @FXML
    void sortValueButtonClicked(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        //Ensures that the correct menu displays upon initializing the program
        homeView.setVisible(true);
        itemsView.setVisible(false);

    }

}
