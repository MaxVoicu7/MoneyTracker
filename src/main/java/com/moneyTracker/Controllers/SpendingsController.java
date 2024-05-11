package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Spending;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SpendingsController implements Initializable {
    @FXML
    private ListView<Spending> spendingsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
