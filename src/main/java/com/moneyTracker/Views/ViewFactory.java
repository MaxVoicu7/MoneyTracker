package com.moneyTracker.Views;

import com.moneyTracker.Controllers.UserController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private AnchorPane dashboardView;
    private AnchorPane spendingsView;
    private AnchorPane accountsView;

    private final ObjectProperty<UserMenuOptions> selectedMenuItem;

    public ViewFactory() {
        this.selectedMenuItem = new SimpleObjectProperty<>();
    }

    public ObjectProperty<UserMenuOptions> getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dashboardView;
    }

    public AnchorPane getSpendingsView() {
        if (spendingsView == null) {
            try {
                spendingsView = new FXMLLoader(getClass().getResource("/Fxml/Spendings.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return spendingsView;
    }

    public AnchorPane getAccountsView() {
        if (accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/Accounts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return accountsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showUserWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/User.fxml"));
        UserController userController = new UserController();
        loader.setController(userController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/money.png"))));
        stage.setResizable(false);
        stage.setTitle("Money Tracker");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
