import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.*;

public class CalculatorUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextField display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "C", "←", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "±", "="
        };

        StringBuilder expression = new StringBuilder();

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch (text) {
                        case "C":
                            expression.setLength(0);
                            display.setText("");
                            break;
                        case "←":
                            if (expression.length() > 0) {
                                expression.setLength(expression.length() - 1);
                                display.setText(expression.toString());
                            }
                            break;
                        case "=":
                            try {
                                ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                                Object result = engine.eval(expression.toString());
                                display.setText(result.toString());
                                expression.setLength(0);
                                expression.append(result.toString());
                            } catch (Exception ex) {
                                display.setText("Error");
                                expression.setLength(0);
                            }
                            break;
                        case "±":
                            if (expression.length() > 0) {
                                if (expression.charAt(0) == '-') {
                                    expression.deleteCharAt(0);
                                } else {
                                    expression.insert(0, "-");
                                }
                                display.setText(expression.toString());
                            }
                            break;
                        case "%":
                            expression.append("/100");
                            display.setText(expression.toString());
                            break;
                        default:
                            expression.append(text);
                            display.setText(expression.toString());
                    }
                }
            });

            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}