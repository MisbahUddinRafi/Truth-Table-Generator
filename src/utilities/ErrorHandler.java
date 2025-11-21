package utilities;

import controllers.ResultPageController;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class ErrorHandler {

    public static void showErrorMessage(String title, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        // Apply stylesheet to the dialog pane:
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                ResultPageController.class.getResource("/view/Styling.css").toExternalForm()
        );

        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

}
