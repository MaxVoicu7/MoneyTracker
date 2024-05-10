package com.moneyTracker.Models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Spending {
    private final StringProperty description;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;
    private final BooleanProperty isSpent;

    public Spending(String description, double amount, LocalDate date, String message, boolean isSpent) {
        this.description = new SimpleStringProperty(this, "description", description);
        this.message = new SimpleStringProperty(this, "message", message);
        this.amount = new SimpleDoubleProperty(this, "amount", amount);
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.isSpent = new SimpleBooleanProperty(this, "is spend", isSpent);
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

    public BooleanProperty getIsSpentProperty() {
        return this.isSpent;
    }
}
