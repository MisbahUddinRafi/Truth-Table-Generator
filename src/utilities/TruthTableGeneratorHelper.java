package utilities;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class TruthTableGeneratorHelper {

    public static boolean isOperator(char ch) {
        return (ch == '+' || ch == '.' || ch == '\'');
    }

    public static boolean isVariable(char ch) {
        return ('A' <= ch && ch <= 'Z') || ('a' <= ch && ch <= 'z');
    }

    // FUNCTION TO GENERATE BINARY OF AN INTEGER:
    public static StringBuilder binaryOf(int n, int numBits) {
        StringBuilder str = new StringBuilder();
        for (int i = numBits - 1; i >= 0; i--) {
            int bit = (n >> i) & 1;
            str.append(bit);
        }
        return str;
    }


    // PRECEDENCE ORDER: NOT > AND > OR
    public static int precedence(char op) {
        if (op == '\'') return 3;
        else if (op == '.') return 2;
        else if (op == '+') return 1;
        return 0;
    }


    // ADD THE MISSING AND OPERATORS IN THE EXPRESSION:
    // BETWEEN TWO VARIABLES: AB -> A.B
    // BETWEEN NOT OPERATOR AND A VARIABLE: A'B -> A'.B
    // BETWEEN NOT OPERATOR AND OPENING BRACKET: A'(B+C)->A'.(B+C)
    // BETWEEN A VARIABLE AND OPENING BRACKET: A(B+C) -> A.(B+C)
    // BETWEEN CLOSING BRACKET AND A VARIABLE: (A+B)C -> (A+B).C
    // BETWEEN CLOSING BRACKET AND OPENING BRACKET: (A+B)(C+D) -> (A+B).(C+D)
    public static StringBuilder validateExpression(StringBuilder expression) {
        if (expression.length() == 0) {
            return expression;
        }
        StringBuilder result = new StringBuilder();
        int size = expression.length();
        for (int i = 0; i < size - 1; i++) {
            char ch = expression.charAt(i);
            char next = expression.charAt(i + 1);
            result.append(ch);

            if (ch == '\'' || isVariable(ch) || ch == ')') {
                if (next == '(' || isVariable(next)) {
                    result.append(".");
                }
            }
        }
        result.append(expression.charAt(size - 1));
        System.out.println(result);
        return result;
    }


    public static boolean isValidExpression(StringBuilder expression) {
        // perform infix to postfix and check validity
        // then perform the postfix evaluation and check validity

        // PART 01:
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (isVariable(ch) || ch == '\'') {
                postfix.append(ch);
            } else if (isOperator(ch)) {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    postfix.append(stack.peek());
                    stack.pop();
                }
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.peek());
                    stack.pop();
                }
                if (stack.isEmpty() || stack.peek() != '(') {
                    ErrorHandler.showErrorMessage("Invalid Expression", "extra closing parenthesis found");
                    return false;
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                ErrorHandler.showErrorMessage("Invalid Expression", "extra opening parenthesis found");
                return false;
            }
            postfix.append(stack.peek());
            stack.pop();
        }


        // PART 02:

        // EVALUATE THE POSTFIX NOTATION:
        Stack<Boolean> valueStack = new Stack<>();
        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);
            if (!isOperator(ch)) valueStack.push(true);
            else {
                if (ch == '\'') {
                    if (valueStack.isEmpty()) {
                        ErrorHandler.showErrorMessage("Invalid Expression", "NOT operator missing operand");
                        return false;
                    }
                    boolean n = valueStack.peek();
                    valueStack.pop();
                    valueStack.push(!n);
                } else if (ch == '.' || ch == '+') {
                    if (valueStack.size() < 2) {
                        ErrorHandler.showErrorMessage("Invalid Expression", "Binary operator missing operands");
                        return false;
                    }
                    boolean m = valueStack.peek();
                    valueStack.pop();
                    boolean n = valueStack.peek();
                    valueStack.pop();
                    if (ch == '.') valueStack.push(m && n);
                    else valueStack.push(m || n);
                }
            }
        }

        if (valueStack.size() != 1) {
            ErrorHandler.showErrorMessage("Invalid Expression", "invalid expression, leftover operands");
            return false;
        }

        return true;
    }


    // FUNCTION TO CONVERT INFIX EXPRESSION TO POSTFIX NOTATION:
    public static StringBuilder infixToPostfix(StringBuilder infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (isVariable(ch)) {
                postfix.append(ch);
            } else if (ch == '\'') {
                postfix.append(ch);
            } else if (isOperator(ch)) {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    postfix.append(stack.peek());
                    stack.pop();
                }
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.peek());
                    stack.pop();
                }
                if (stack.isEmpty() || stack.peek() != '(') {
                    System.out.println("invalid expression");
                    System.exit(0);
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                System.out.println("invalid parenthesis matching");
                System.exit(0);
            }
            postfix.append(stack.peek());
            stack.pop();
        }

        return postfix;
    }


    //  FUNCTION TO EVALUATE THE POSTFIX EXPRESSION:
    public static int result(StringBuilder bits, List<Character> variables, StringBuilder postfix) {
        Map<Character, Boolean> map = new HashMap<>();

        for (int i = 0; i < variables.size(); i++) {
            map.put(variables.get(i), bits.charAt(i) == '1');
        }


        // EVALUATE THE POSTFIX NOTATION:
        Stack<Boolean> valueStack = new Stack<>();
        for (int i = 0; i < postfix.length(); i++) {
            char ch = postfix.charAt(i);
            if (!isOperator(ch)) valueStack.push(map.get(ch));
            else {
                if (valueStack.isEmpty()) {
                    System.out.println("wrong input, no operands left for operation");
                    System.exit(0);
                }
                if (ch == '\'') {
                    boolean n = valueStack.peek();
                    valueStack.pop();
                    valueStack.push(!n);
                } else if (ch == '.') {
                    if (valueStack.size() < 2) {
                        System.out.println("wrong input, not enough operand for . operator");
                        System.exit(0);
                    }
                    boolean m = valueStack.peek();
                    valueStack.pop();
                    boolean n = valueStack.peek();
                    valueStack.pop();
                    valueStack.push(m && n);
                } else if (ch == '+') {
                    if (valueStack.size() < 2) {
                        System.out.println("wrong input, not enough operands for + operator");
                        System.exit(0);
                    }
                    boolean m = valueStack.peek();
                    valueStack.pop();
                    boolean n = valueStack.peek();
                    valueStack.pop();
                    valueStack.push(m || n);
                }
            }
        }

        if (valueStack.size() != 1) {
            System.out.println("after result calculation, there are more than one result in the value stack, so wrong expression");
            System.exit(0);
        }

        return (valueStack.peek() ? 1 : 0);
    }

    private static List<Character> countVariables(StringBuilder expression) {
        Set<Character> literals = new HashSet<>();      // stores only unique elements
        int size = expression.length();
        for (int i = 0; i < size; i++) {
            if (isVariable(expression.charAt(i))) {
                literals.add(expression.charAt(i));
            }
        }

        // SORT THE VARIABLES:
        List<Character> variables = new ArrayList<>(literals);
        Collections.sort(variables);

        return variables;
    }


    public static class TruthRow {
        public StringBuilder bits;   // binary string (e.g., 0101)
        public int result;           // F value

        public TruthRow(StringBuilder bits, int result) {
            this.bits = bits;
            this.result = result;
        }
    }


    public static void generateTable(TableView<TruthRow> table,
                                     StringBuilder postfix) {

        table.getColumns().clear();
        table.getItems().clear();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        List<Character> variables = countVariables(postfix);
        int n = variables.size();
        System.out.println(postfix);
        System.out.println(variables.toString());
        System.out.println(variables.size());

        // ----------------------------
        // CREATE COLUMNS
        // ----------------------------
        for (int col = 0; col < n; col++) {
            final int index = col;
            char varName = variables.get(col);

            TableColumn<TruthRow, String> column =
                    new TableColumn<>(String.valueOf(varName));

            column.setCellValueFactory(cell ->
                    new SimpleStringProperty(
                            String.valueOf(cell.getValue().bits.charAt(index))
                    ));

            // CENTER + FONT
//            column.setCellFactory(colm -> new TableCell<TruthRow, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty || item == null) {
//                        setText(null);
//                        return;
//                    }
//                    setText(item);
//                    setFont(Font.font("Comic Sans MS", 20));  // FONT HERE
//                    setStyle("-fx-alignment: CENTER;");
//                }
//            });

            table.getColumns().add(column);
        }

        // F column
        TableColumn<TruthRow, String> fCol = new TableColumn<>("F");
        fCol.setCellValueFactory(cell ->
                new SimpleStringProperty(String.valueOf(cell.getValue().result)));

        fCol.setCellFactory(col -> new TableCell<TruthRow, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    return;
                }
                setText(item);
                setFont(Font.font("Comic Sans MS", 20));
                setStyle("-fx-alignment: CENTER;");
            }
        });

        table.getColumns().add(fCol);

        // ----------------------------
        // GENERATE TABLE ROWS
        // ----------------------------
        int rowCount = 1 << n; // 2^n
        table.setFixedCellSize(35); // or any height
        table.prefHeightProperty().bind(
                table.fixedCellSizeProperty().multiply(rowCount + 1.1)
        );


        for (int i = 0; i < rowCount; i++) {
            StringBuilder bits = binaryOf(i, n);
            System.out.print(bits + " ");
            int value = result(bits, variables, postfix);
            System.out.print(value);
            System.out.println();
            table.getItems().add(new TruthRow(bits, value));
        }
    }


}


