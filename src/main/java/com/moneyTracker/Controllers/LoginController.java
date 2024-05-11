package com.moneyTracker.Controllers;

import com.moneyTracker.CustomExceptions.ValidationException;
import com.moneyTracker.Models.Model;
import com.moneyTracker.Utils.ValidationUtils;
import com.moneyTracker.Views.OptionType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;
    @FXML
    private ChoiceBox<OptionType> optionSelector;
    @FXML
    private Button optionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optionSelector.setItems(FXCollections.observableArrayList(OptionType.LOGIN, OptionType.SIGNUP));
        optionSelector.setValue(OptionType.LOGIN);
        setConfirmPasswordVisibility(OptionType.LOGIN);

        optionSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            setConfirmPasswordVisibility(newVal);
        });

        optionButton.setOnAction(event -> {
            if (optionSelector.getValue() == OptionType.LOGIN) {
                onLogin();
            } else if (optionSelector.getValue() == OptionType.SIGNUP) {
                onSignup();
            }
        });
    }

    private void onLogin() {
        try {
            ValidationUtils.validateEmail(emailTextField.getText());
            ValidationUtils.validatePassword(passwordField.getText());

            Stage stage = (Stage) optionButton.getScene().getWindow();
            Model.getInstance().evaluateUserCredentials(emailTextField.getText(), passwordField.getText());

            if (Model.getInstance().getIsUserLoggedIn()) {
                errorLabel.setText("");
                successLabel.setText("");
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showUserWindow();
            } else {
                throw new ValidationException("Invalid credentials");
            }
        } catch (ValidationException e) {
            errorLabel.setText(e.getMessage());
        } finally {
            emailTextField.setText("");
            passwordField.setText("");
        }
    }

    private void onSignup() {
        try {
            ValidationUtils.validateEmail(emailTextField.getText());
            ValidationUtils.validatePassword(passwordField.getText());
            ValidationUtils.validateConfirmPassword(passwordField.getText(), confirmPasswordField.getText());

            boolean registration = Model.getInstance().registerUser(emailTextField.getText(), passwordField.getText());

            if (registration) {
                errorLabel.setText("");
                successLabel.setText("Signup successful. Please login");
                optionSelector.setValue(OptionType.LOGIN);
            } else {
                errorLabel.setText("Signup failed. User might already exist");
            }
        } catch (ValidationException e) {
            errorLabel.setText(e.getMessage());
            successLabel.setText("");
        } finally {
            emailTextField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }

    private void setConfirmPasswordVisibility(OptionType optionType) {
        if (optionType == OptionType.LOGIN) {
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
            confirmPasswordLabel.setVisible(false);
            confirmPasswordLabel.setManaged(false);
            optionButton.setText("Log in");
        } else {
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            confirmPasswordLabel.setVisible(true);
            confirmPasswordLabel.setManaged(true);
            optionButton.setText("Sign up");
        }

        errorLabel.setText("");
    }
}
