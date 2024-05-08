module com.moneyTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.moneyTracker.Controllers to javafx.fxml;
    exports com.moneyTracker;
    exports com.moneyTracker.Controllers;
    exports com.moneyTracker.Models;
    exports com.moneyTracker.Views;
}