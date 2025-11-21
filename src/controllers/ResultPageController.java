package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import utilities.TruthTableGeneratorHelper;
import utilities.Main;

public class ResultPageController {
    @FXML
    TableView<TruthTableGeneratorHelper.TruthRow> resultTable;
    @FXML
    TextField expressionText;
    @FXML
    TextField postfixText;

    @FXML
    public void initialize() {
        String postfix = TruthTableGeneratorHelper.infixToPostfix(Main.expression).toString();
        System.out.println(Main.expression);
        System.out.println(postfix);

        expressionText.setText(Main.expression.toString());
        postfixText.setText(postfix);

        TruthTableGeneratorHelper.generateTable(resultTable, new StringBuilder(postfix));
    }

    public void goToMain() {
        Main.goBack();
    }


}