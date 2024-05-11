package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Model;
import com.moneyTracker.Views.UserMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        accountsButton.setOnAction(event -> onAccounts());
        logoutButton.setOnAction(event -> onLogout());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(UserMenuOptions.DASHBOARD);
    }

    private void onSpendings() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(UserMenuOptions.SPENDINGS);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(UserMenuOptions.ACCOUNTS);
    }

    private void onLogout() {
        Stage stage = (Stage) dashboardButton.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().setIsUserLoggedIn(false);
    }
}
