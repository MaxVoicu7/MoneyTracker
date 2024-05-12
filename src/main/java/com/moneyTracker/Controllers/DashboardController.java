package com.moneyTracker.Controllers;

import com.moneyTracker.CustomExceptions.ValidationException;
import com.moneyTracker.Models.Model;
import com.moneyTracker.Models.Spending;
import com.moneyTracker.Utils.ValidationUtils;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import com.moneyTracker.Models.Account;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private ScrollPane spendingScrollPane;
    @FXML
    private VBox spendingContainer;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField accountTextField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button addSpendingButton;
    @FXML
    private AnchorPane card1Container;
    @FXML
    private AnchorPane card2Container;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        updateAccountDisplays();
        displaySpendings();

        errorLabel.setText("");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        addSpendingButton.setOnAction(event -> onCreateNewSpending());
    }

    private void displaySpendings() {
        List<Spending> spendings = Model.getInstance().getUserSpendings();
        spendingContainer.getChildren().clear();

        for (Spending spending : spendings) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SpendingCell.fxml"));
                AnchorPane spendingPane = loader.load();

                SpendingCellController controller = loader.getController();
                controller.updateSpending(spending);

                spendingContainer.getChildren().add(spendingPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCreateNewSpending() {
        try {
            ValidationUtils.validateCategory(categoryTextField.getText());
            ValidationUtils.validateAmount(amountTextField.getText());
            ValidationUtils.validateAccountId(accountTextField.getText());

            List<Account> accounts = Arrays.asList(Model.getInstance().getUserAccount());

            String accountNumber = accountTextField.getText();
            double amount = Double.parseDouble(amountTextField.getText());

            Optional<Account> matchingAccount = accounts.stream()
                    .filter(acc -> acc.getNumberProperty().get().equals(accountNumber))
                    .findFirst();

            if (matchingAccount.isEmpty()) {
                throw new ValidationException("Account number does not exist.");
            }

            if (matchingAccount.get().getBalanceProperty().get() < amount) {
                throw new ValidationException("Amount exceeds the account balance.");
            }

            Account foundAccount = matchingAccount.get();
            int accountId = foundAccount.getId();

            boolean spendingCreated = Model.getInstance().createSpending(accountId, categoryTextField.getText(),
                    messageTextArea.getText(), Double.parseDouble(amountTextField.getText()));

            if (spendingCreated) {
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                errorLabel.setText("Created spending");
                clearSpendingFields();

                updateAccountDisplays();
                displaySpendings();
            } else {
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                errorLabel.setText("Failed to create account");
            }

        } catch (ValidationException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    private void clearSpendingFields() {
        categoryTextField.setText("");
        messageTextArea.setText("");
        accountTextField.setText("");
        amountTextField.setText("");
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
