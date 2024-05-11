package com.moneyTracker.Models;

import com.moneyTracker.Views.OptionType;
import com.moneyTracker.Views.ViewFactory;

import java.sql.ResultSet;

public class Model {
    private final ViewFactory viewFactory;
    private static Model model;
    private final DatabaseDriver databaseDriver;
    private OptionType optionSelected = OptionType.LOGIN;
    private User user;
    private boolean isUserLoggedIn = false;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        this.isUserLoggedIn = false;
        this.user = new User("", "");
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public OptionType getOptionSelected() {
        return this.optionSelected;
    }

    public void setOptionSelected(OptionType option) {
        this.optionSelected = option;
    }

    public boolean getIsUserLoggedIn() {
        return this.isUserLoggedIn;
    }

    public void setIsUserLoggedIn(boolean status) {
        this.isUserLoggedIn = status;
    }

    public User getUser() {
        return user;
    }

    public void evaluateUserCredentials(String username, String password) {
        ResultSet resultSet = databaseDriver.getUserData(username, password);

        try {
            if (resultSet.isBeforeFirst()) {
                this.user.id = resultSet.getInt("id");
                this.isUserLoggedIn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        // You can add more logic here for validation or checking if the user already exists
        return this.databaseDriver.createUser(username, password);
    }
}
