module com.moneytracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.moneyTracker to javafx.fxml;
    exports com.moneyTracker;
    exports com.moneyTracker.Controllers;
    exports com.moneyTracker.Models;
    exports com.moneyTracker.Views;
}