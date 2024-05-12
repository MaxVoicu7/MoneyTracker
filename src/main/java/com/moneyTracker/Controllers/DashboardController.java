package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Model;
import com.moneyTracker.Models.Spending;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import com.moneyTracker.Models.Account;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
    private Label totalBalanceLabel;
    @FXML
    private Label totalAccountsLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<Spending> transactionListView;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button addSpendingButton;
    @FXML
    private AnchorPane card1Container;
    @FXML
    private AnchorPane card2Container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        updateAccountDisplays();
    }

    private void bindData() {
        loginDateLabel.setText(LocalDate.now() + "");
    }

    private void updateAccountDisplays() {
        List<Account> accounts = Arrays.asList(Model.getInstance().getUserAccount());

        int totalNumberOfAccounts = accounts.size();
        double totalBalance = accounts.stream()
                .mapToDouble(account -> account.getBalanceProperty().get())
                .sum();

        if (!accounts.isEmpty()) {
            setAccountData(card1BalanceLabel, card1IdLabel, card1TypeLabel, accounts.getFirst());
            card1Container.setVisible(true);
        } else {
            card1Container.setVisible(false);
        }

        if (accounts.size() > 1) {
            setAccountData(card2BalanceLabel, card2IdLabel, card2TypeLabel, accounts.get(1));
            card2Container.setVisible(true);
        } else {
            card2Container.setVisible(false);
        }

        displayAccountInfo(totalNumberOfAccounts, totalBalance);
    }

    private void setAccountData(Label balanceLabel, Label idLabel, Label typeLabel, Account account) {
        balanceLabel.setText(String.format("%.2f lei", account.getBalanceProperty().get()));
        idLabel.setText(account.getNumberProperty().get().substring(account.getNumberProperty().get().length() - 4));
        typeLabel.setText(account.getTypeProperty().get());
    }

    private void displayAccountInfo(int accountsNr, double accountsBalance) {
        totalBalanceLabel.setText(accountsBalance + " lei");
        totalAccountsLabel.setText(accountsNr + " accounts");
    }
}
