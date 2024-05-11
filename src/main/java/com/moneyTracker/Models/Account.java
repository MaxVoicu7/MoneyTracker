package com.moneyTracker.Models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.YearMonth;

public class Account {
    private final StringProperty ownerName;
    private final StringProperty number;
    private final StringProperty type;
    private final ObjectProperty<YearMonth> expirationDate;
    private final DoubleProperty balance;

    public Account(String owner, String nr, String type, YearMonth expDate, double balance) {
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

    public ObjectProperty<YearMonth> getExpirationDateProperty() {
        return this.expirationDate;
    }

    public DoubleProperty getBalanceProperty() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ownerName=" + ownerName.get() +
                ", number=" + number.get() +
                ", type=" + type.get() +
                ", expirationDate=" + expirationDate.get() +
                ", balance=" + balance.get() +
                '}';
    }
}
