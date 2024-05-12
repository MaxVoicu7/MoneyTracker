package com.moneyTracker.Views;

import com.moneyTracker.Controllers.SpendingCellController;
import com.moneyTracker.Models.Spending;
import javafx.scene.control.ListCell;
import javafx.fxml.FXMLLoader;

public class SpendingCellFactory extends ListCell<Spending> {
    @Override
    protected void updateItem(Spending spending, boolean empty) {
        super.updateItem(spending, empty);

        System.out.println("UpdateItem called: empty = " + empty + ", spending = " + (spending != null));

        if (empty || spending == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SpendingCell.fxml"));
            loader.setController(new SpendingCellController());
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
