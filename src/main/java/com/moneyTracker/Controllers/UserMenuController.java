package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Model;
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
        addListeners();
    }

    private void addListeners() {
        dashboardButton.setOnAction(event -> onDashboard());
        spendingsButton.setOnAction(event -> onSpendings());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Dashboard");
    }

    private void onSpendings() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set("Spendings");
    }
}
