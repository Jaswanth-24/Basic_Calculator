import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class MyFrame{
    JFrame frame;
    JTextField field;
    JTextField historyField;
    JPanel buttonPanel;
    JButton btn;

    double num1 = 0, num2 = 0, result = 0;
    String operator = "";
    boolean startNewNumber = true;

    public MyFrame(){
        setupFrame();
        setupField();
        setupButtons();
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                KeyPressed(e);
            }
        });
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }
    //Frame
    private void setupFrame() {
        frame = new JFrame("Calculator");
        frame.setSize(350, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    //TextField
    private void setupField() {

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.BLACK);

        // History TextField
        historyField = new JTextField();
        historyField.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        historyField.setEditable(false);
        historyField.setHorizontalAlignment(SwingConstants.RIGHT);
        historyField.setBorder(null);
        historyField.setBackground(Color.BLACK);
        historyField.setForeground(Color.LIGHT_GRAY);

        // Main TextField
        field = new JTextField();
        field.setEditable(true);
        field.setFont(new Font("Cambria Math", Font.PLAIN, 30));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        topPanel.add(historyField);
        topPanel.add(field);

        // Add topPanel to frame
        frame.add(topPanel, BorderLayout.NORTH);
    }

    //Buttons
    private void setupButtons() {
        //Logic to Create Buttons
        String[] buttons = {
                "AC", "+/-", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "_",
                "1", "2", "3", "+",
                "Cal", "0", ".", "="
        };
        buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        for (String text : buttons) {
            btn = new JButton(text);
            btn.setFont(new Font("Cambria Math", Font.BOLD, 20));
            btn.setFocusable(false);

            // Set Button Colors
            if (text.matches("[/*_+%=]")) {
                btn.setBackground(Color.ORANGE);
                btn.setForeground(Color.WHITE);
            } else if (text.equals("ac") || text.equals("+/-") || text.equals("%")) {
                btn.setBackground(Color.LIGHT_GRAY);
            } else {
                btn.setBackground(Color.DARK_GRAY);
                btn.setForeground(Color.WHITE);
            }

            // Action Listener
            btn.addActionListener(e -> {
                String cmd = e.getActionCommand();
                if (cmd.matches(("[0-9]"))) {
                    if (startNewNumber) {
                        field.setText(cmd);
                        startNewNumber = false;
                    } else {
                        field.setText(field.getText() + cmd);
                    }
                } else if (cmd.equals(".")) {
                    if (!field.getText().contains(".")) {
                        field.setText((field.getText() + "."));
                    }
                } else if (cmd.equals(("AC"))) {
                    field.setText("");
                    num1 = num2 = result = 0;
                    operator = "";
                    startNewNumber = true;
                } else if (cmd.matches("[/*_+%]")) {
                    num1 = Double.parseDouble((field.getText()));
                    operator = cmd;
                    historyField.setText(field.getText() + " " + operator);
                    startNewNumber = true;
                } else if (cmd.equals("=")) {
                    num2 = Double.parseDouble(field.getText());
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "_":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            result = num2 != 0 ? num1 / num2 : 0;
                            break;
                        case "%":
                            result = num1 % num2;
                            break;
                    }
                    historyField.setText(historyField.getText() + " " + field.getText() + " =");
                    field.setText(String.valueOf(result));
                    startNewNumber = true;
                }
            });
            buttonPanel.add(btn);
        }
        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    //Keyboard Functionality
    private void KeyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if (startNewNumber) {
                field.setText(String.valueOf(keyChar));
                startNewNumber = false;
            } else {
                field.setText(field.getText() + keyChar);
            }
        } else if (keyChar == '.') {
            if (!field.getText().contains(".")) {
                field.setText(field.getText() + ".");
            }
        } else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/' || keyChar == '%') {
            num1 = Double.parseDouble(field.getText());

            switch (keyChar) {
                case '+':
                    operator = "+";
                    break;
                case '-':
                    operator = "−";
                    break;
                case '*':
                    operator = "×";
                    break;
                case '/':
                    operator = "÷";
                    break;
                case '%':
                    operator = "%";
                    break;
            }
            historyField.setText(field.getText() + " " + operator);
            startNewNumber = true;
        } else if (keyChar == '\n' || keyChar == '=') {
            num2 = Double.parseDouble(field.getText());

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "−":
                    result = num1 - num2;
                    break;
                case "×":
                    result = num1 * num2;
                    break;
                case "÷":
                    result = num2 != 0 ? num1 / num2 : 0;
                    break;
                case "%":
                    result = num1 % num2;
                    break;
            }
            historyField.setText(historyField.getText() + " " + field.getText() + " =");
            field.setText(String.valueOf(result));
            startNewNumber = true;
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            String current = field.getText();
            if (current.length() > 0) {
                field.setText(current.substring(0, current.length() - 1));
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            field.setText("");
            historyField.setText("");
            num1 = num2 = result = 0;
            operator = "";
            startNewNumber = true;
        }
    }
}