package com.moneyTracker.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label loginDateLabel;
    @FXML
    private Label card1BalanceLabel;
    @FXML
    private Label card1IdLabel;
    @FXML
    private Label card1TypeLabel;
    @FXML
    private Label card2BalanceLabel;
    @FXML
    private Label card2IdLabel;
    @FXML
    private Label card2TypeLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label expenseLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<String> transactionListView;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button addSpendingButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
