package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Spending;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListCell;

public class SpendingCellController {
    @FXML
    private Label spendingDayLabel;
    @FXML
    private Label spendingAmountLabel;
    @FXML
    private Label spendingNameLabel;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void updateSpending(Spending spending) {
        spendingDayLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        spending.getDateProperty().get() != null ? spending.getDateProperty().get().format(dateFormatter) : "",
                spending.getDateProperty()));

        spendingNameLabel.textProperty().bind(spending.getDescriptionProperty());
        spendingAmountLabel.textProperty().bind(spending.getAmountProperty().asString("%.2f lei"));
    }
}

