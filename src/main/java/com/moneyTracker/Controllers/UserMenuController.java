package com.moneyTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    @FXML
    private Button dashboardButton;
    @FXML
    private Button spendingsButton;
    @FXML
    private Button accountsButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
