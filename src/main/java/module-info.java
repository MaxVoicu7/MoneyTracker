module com.moneytracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.moneytracker to javafx.fxml;
    exports com.moneytracker;
}