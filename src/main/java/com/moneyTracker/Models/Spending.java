package com.moneyTracker.Models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Spending {
    private final StringProperty description;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;
    private final StringProperty account;

    public Spending(String description, double amount, LocalDate date, String message, String account) {
        this.description = new SimpleStringProperty(this, "description", description);
        this.message = new SimpleStringProperty(this, "message", message);
        this.amount = new SimpleDoubleProperty(this, "amount", amount);
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.account = new SimpleStringProperty(this, "account", account);
    }

    public StringProperty getDescriptionProperty() {
        return this.description;
    }

    public DoubleProperty getAmountProperty() {
        return this.amount;
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return this.date;
    }

    public StringProperty getMessage() {
        return this.message;
    }

    public StringProperty getAccount() {
        return this.account;
    }
}
