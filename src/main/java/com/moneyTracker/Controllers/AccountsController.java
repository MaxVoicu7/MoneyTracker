package com.moneyTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    @FXML
    private Text account1NameText;
    @FXML
    private Text account2NameText;
    @FXML
    private Label account1IdLabel;
    @FXML
    private Label account1DateLabel;
    @FXML
    private Label account1OwnerLabel;
    @FXML
    private Label account1BalanceLabel;
    @FXML
    private Label account2IdLabel;
    @FXML
    private Label account2DateLabel;
    @FXML
    private Label account2OwnerLabel;
    @FXML
    private Label account2BalanceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
