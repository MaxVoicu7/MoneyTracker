package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Spending;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SpendingCellController implements Initializable {
    @FXML
    private Label spendingDayLabel;
    @FXML
    private Label spendingAmountLabel;
    @FXML
    private Label spendingNameLabel;
    @FXML
    private FontAwesomeIconView inArrow;
    @FXML
    private FontAwesomeIconView outArrow;

    private final Spending spending;

    public SpendingCellController(Spending spending) {
        this.spending = spending;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
