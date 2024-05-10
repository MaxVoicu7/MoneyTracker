package com.moneyTracker.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty email;
    private final StringProperty password;

    public User(String email, String password) {
        this.email = new SimpleStringProperty(this, "email", email);
        this.password = new SimpleStringProperty(this, "password", password);
    }

    public StringProperty getEmailProperty() {
        return this.email;
    }

    public StringProperty getPasswordProperty() {
        return this.password;
    }
}
