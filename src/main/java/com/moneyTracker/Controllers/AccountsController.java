package com.moneyTracker.Controllers;

import com.moneyTracker.CustomExceptions.ValidationException;
import com.moneyTracker.Models.Account;
import com.moneyTracker.Models.Model;
import com.moneyTracker.Utils.ValidationUtils;
import com.moneyTracker.Views.AccountType;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.YearMonth;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    @FXML
    private Label accountsLabel;
    @FXML
    private VBox accountsVBox;
    @FXML
    private TextField accountIdTextField;
    @FXML
    private TextField accountOwnerTextField;
    @FXML
    private TextField accountDateTextField;
    @FXML
    private TextField accountBalanceTextField;
    @FXML
    private Button createAccountButton;
    @FXML
    private ChoiceBox<AccountType> accountTypeChoiceBox;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountTypeChoiceBox.setItems(FXCollections.observableArrayList(AccountType.MICB, AccountType.MAIB));
        accountTypeChoiceBox.setValue(AccountType.MAIB);

        displayAccounts(Arrays.asList(Model.getInstance().getUserAccount()));

        createAccountButton.setOnAction(event -> onCreateNewAccount());

        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        errorLabel.setText("");
        successLabel.setVisible(false);
        successLabel.setManaged(false);
        successLabel.setText("");
    }

    private void onCreateNewAccount() {
        try {
            ValidationUtils.validateAccountId(accountIdTextField.getText());
            ValidationUtils.validateAccountOwner(accountOwnerTextField.getText());
            ValidationUtils.validateExpirationDate(accountDateTextField.getText());
            ValidationUtils.validateBalance(accountBalanceTextField.getText());

            boolean accountCreated = Model.getInstance().createAccount(accountIdTextField.getText(),
                    accountOwnerTextField.getText(), accountTypeChoiceBox.getValue(),
                    Double.parseDouble(accountBalanceTextField.getText()), accountDateTextField.getText());

            if (accountCreated) {
                successLabel.setVisible(true);
                successLabel.setManaged(true);
                successLabel.setText("Account created successfully");
                errorLabel.setVisible(false);
                errorLabel.setManaged(false);
                errorLabel.setText("");
                clearAccountFields();

                displayAccounts(Arrays.asList(Model.getInstance().getUserAccount()));
            } else {
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
                successLabel.setVisible(false);
                successLabel.setManaged(false);
                successLabel.setText("");
                errorLabel.setText("Failed to create account");
            }
        } catch (ValidationException e) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            errorLabel.setText(e.getMessage());
        }
    }

    private void clearAccountFields() {
        accountIdTextField.setText("");
        accountOwnerTextField.setText("");
        accountDateTextField.setText("");
        accountBalanceTextField.setText("");
    }

    public void displayAccounts(List<Account> accounts) {
        accountsVBox.getChildren().clear();

        for (Account account : accounts) {
            VBox accountBox = new VBox(10);
            accountBox.setPadding(new Insets(10));
            accountBox.getStyleClass().add("account");

            Text nameText = new Text();
            nameText.textProperty().bind(account.getTypeProperty().concat(" Account"));

            Text idText = new Text("Account id:");
            Label idLabel = new Label();
            idLabel.textProperty().bind(Bindings.createStringBinding(() -> {
                String number = account.getNumberProperty().get();
                return number == null || number.length() < 4 ? "**** **** **** ****" :
                        "**** **** **** " + number.substring(number.length() - 4);
            }, account.getNumberProperty()));

            Text expirationDateText = new Text("Account expiration date:");
            Label dateLabel = new Label();
            dateLabel.textProperty().bind(Bindings.createStringBinding(() -> {
                YearMonth expDate = account.getExpirationDateProperty().get();
                return expDate != null ? expDate.format(DateTimeFormatter.ofPattern("MM/yy")) : "";
            }, account.getExpirationDateProperty()));

            Text ownerText = new Text("Account owner:");
            Label ownerLabel = new Label();
            ownerLabel.textProperty().bind(account.getOwnerNameProperty());
            Text balanceText = new Text("Balance:");
            Label balanceLabel = new Label();
            balanceLabel.textProperty().bind(account.getBalanceProperty().asString("%.2f lei"));

            accountBox.getChildren().addAll(nameText, idText, idLabel, expirationDateText, dateLabel,
                    ownerText, ownerLabel, balanceText, balanceLabel);

            accountsVBox.getChildren().add(accountBox);
        }
    }
}
