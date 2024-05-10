package com.moneyTracker.Views;

import com.moneyTracker.Controllers.SpendingCellController;
import com.moneyTracker.Models.Spending;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class SpendingCellFactory extends ListCell<Spending> {
    @Override
    protected void updateItem(Spending spending, boolean empty) {
        super.updateItem(spending, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SpendingCell"));
            SpendingCellController controller = new SpendingCellController(spending);
            loader.setController(controller);
            setText(null);

            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
