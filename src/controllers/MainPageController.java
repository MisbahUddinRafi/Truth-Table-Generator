package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utilities.ErrorHandler;
import utilities.TruthTableGeneratorHelper;
import utilities.Main;

public class MainPageController {
    @FXML
    TextField textFieldString;

    public void onGenerate() {
        StringBuilder expressionString = TruthTableGeneratorHelper.validateExpression(new StringBuilder(textFieldString.getText()));

        if (expressionString.length() == 0) {
            ErrorHandler.showErrorMessage("Invalid Input", "Please enter a valid expression.");
            return;
        }
        Main.expression.delete(0, Main.expression.length());
        Main.expression.append(expressionString);

        if (TruthTableGeneratorHelper.isValidExpression(Main.expression)) {
            Main.setRoot("view/resultPage.fxml");
        }
    }

    public void onClearAll() {
        textFieldString.clear();
        Main.expression.delete(0, Main.expression.length());
    }

    public void EnterOperator(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        int pos = textFieldString.getCaretPosition();
        StringBuilder str = new StringBuilder(textFieldString.getText());

        if (button.getId().equalsIgnoreCase("ANDoperator")) {
            str.insert(pos, ".");
        } else if (button.getId().equalsIgnoreCase("ORoperator")) {
            str.insert(pos, "+");
        } else if (button.getId().equalsIgnoreCase("NOToperator")) {
            str.insert(pos, "\'");
        } else if (button.getId().equalsIgnoreCase("openingBracket")) {
            str.insert(pos, "(");
        } else if (button.getId().equalsIgnoreCase("closingBracket")) {
            str.insert(pos, ")");
        } else {
            return;
        }
        textFieldString.setText(str.toString());
        textFieldString.positionCaret(pos + 1);
    }


//    public void goToAboutUs() {
//        Parent root = Main.primaryStage.getScene().getRoot();
//
//        TranslateTransition slide = new TranslateTransition(Duration.millis(300), root);
//        slide.setFromY(0);
//        slide.setToY(-600);   // slide upward
//
//        slide.setOnFinished(e -> Main.setRoot("view/aboutUs.fxml"));
//        slide.play();
//    }


}
