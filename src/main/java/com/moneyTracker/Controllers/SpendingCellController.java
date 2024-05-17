package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Spending;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class SpendingCellController {
    @FXML
    private Label spendingDayLabel;
    @FXML
    private Label spendingAmountLabel;
    @FXML
    private Label spendingNameLabel;
    @FXML
    private Label spendingAccountLabel;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void updateSpending(Spending spending) {
        spendingDayLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        spending.getDateProperty().get() != null ? spending.getDateProperty().get().format(dateFormatter) : "",
                spending.getDateProperty()));

        spendingNameLabel.textProperty().bind(spending.getDescriptionProperty());
        spendingAmountLabel.textProperty().bind(spending.getAmountProperty().asString("%.2f lei"));

        spendingAccountLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            String account = spending.getAccount().get();
            if (account == null || account.length() < 4) {
                return account;
            }
            return "**** **** **** " + account.substring(account.length() - 4);
        }, spending.getAccount()));
    }
}
