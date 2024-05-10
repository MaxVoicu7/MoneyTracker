package com.moneyTracker.Controllers;

import com.moneyTracker.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox<String> optionSelector;
    @FXML
    private Button optionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optionButton.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage stage =(Stage) optionButton.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showUserWindow();
    }
}
