package com.moneyTracker.Models;

import com.moneyTracker.Views.AccountType;
import com.moneyTracker.Views.OptionType;
import com.moneyTracker.Views.ViewFactory;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.util.List;

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

                List<Account> userAccounts = databaseDriver.getAccountsByUserId(this.user.id);
                this.user.setAccounts(userAccounts.toArray(new Account[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        // You can add more logic here for validation or checking if the user already exists
        return this.databaseDriver.createUser(username, password);
    }

    public boolean createAccount(String accId, String accOwner, AccountType accType, double balance, String expDate) {
        if (this.databaseDriver.createAccount(this.user.id, accId, accOwner, expDate, accType, balance)) {
            List<Account> userAccounts = databaseDriver.getAccountsByUserId(this.user.id);
            this.user.setAccounts(userAccounts.toArray(new Account[0]));
            return true;
        }

        return false;
    }

    public boolean createSpending(int accId, String description, String message, double amount) {
        if (this.databaseDriver.createSpending(accId, description, message, amount)) {
            List<Account> userAccounts = databaseDriver.getAccountsByUserId(this.user.id);
            this.user.setAccounts(userAccounts.toArray(new Account[0]));
            return true;
        }

        return false;
    }

    public Account[] getUserAccount() {
        return this.user.getAccounts();
    }

    public StringProperty getUsername() {
        return this.user.getEmailProperty();
    }

    public List<Spending> getUserSpendings() {
        return this.getDatabaseDriver().getAllSpendingsForUser(this.user.id);
    }
}
