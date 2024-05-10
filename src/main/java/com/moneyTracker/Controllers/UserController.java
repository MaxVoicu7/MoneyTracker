package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private BorderPane userBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSelectedMenuItem().addListener(
                (observableValue, oldValue, newValue) -> {
                    switch (newValue) {
                        case "Spendings" -> userBorderPane.setCenter(Model.getInstance().getViewFactory().getSpendingsView());
                        case "Accounts" -> userBorderPane.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                        default -> userBorderPane.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                    }
                });
    }
}
