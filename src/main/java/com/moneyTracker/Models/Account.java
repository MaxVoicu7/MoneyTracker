package com.moneyTracker.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Account {
    private final StringProperty ownerName;
    private final StringProperty number;
    private final StringProperty type;
    private final ObjectProperty<LocalDate> expirationDate;
    private final DoubleProperty balance;

    public Account(String owner, String nr, String type, LocalDate expDate, double balance) {
        this.ownerName = new SimpleStringProperty(this, "owner name", owner);
        this.number = new SimpleStringProperty(this, "number", nr);
        this.type = new SimpleStringProperty(this, "type", type);
        this.expirationDate = new SimpleObjectProperty<>(this, "expiration date", expDate);
        this.balance = new SimpleDoubleProperty(this, "balance", balance);
    }

    public StringProperty getOwnerNameProperty() {
        return this.ownerName;
    }

    public StringProperty getNumberProperty() {
        return this.number;
    }

    public StringProperty getTypeProperty() {
        return this.type;
    }

    public ObjectProperty<LocalDate> getExpirationDateProperty() {
        return this.expirationDate;
    }

    public DoubleProperty getBalanceProperty() {
        return this.balance;
    }
}
