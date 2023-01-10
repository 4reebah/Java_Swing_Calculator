import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Uses Java Swing to create a simple arithmetic character 
 */
public class Calculator extends JFrame {

    private Double firstOperand;
    private String currentOperation;

    private JTextField outputField;
    private ArrayList<JButton> digits;
    private JMenuBar menuBar;
    private JButton plusSign;
    private JButton minusSign;
    private JButton multSign;
    private JButton divSign;
    private JButton eqSign;

    public Calculator() {        
        firstOperand = 0.0;
        currentOperation = "";

        // creating outputfield
        JPanel displayPanel = new JPanel(new FlowLayout());

        outputField = new JTextField("0", 20);
        displayPanel.add(outputField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        // creating digit button panel 
        JPanel digitButtonPanel = new JPanel(new GridLayout(3, 4));

        digits = new ArrayList<JButton>();
        digits.add(new JButton("0"));
        digits.add(new JButton("1"));
        digits.add(new JButton("2"));
        digits.add(new JButton("3"));
        digits.add(new JButton("4"));
        digits.add(new JButton("5"));
        digits.add(new JButton("6"));
        digits.add(new JButton("7"));
        digits.add(new JButton("8"));
        digits.add(new JButton("9"));
        digits.add(new JButton("."));
        digits.add(new JButton("AC"));

        digits.get(10).setForeground(Color.MAGENTA);
        digits.get(11).setForeground(Color.MAGENTA);

        digitButtonPanel.add(digits.get(0));
        digitButtonPanel.add(digits.get(1));
        digitButtonPanel.add(digits.get(2));
        digitButtonPanel.add(digits.get(3));
        digitButtonPanel.add(digits.get(4));
        digitButtonPanel.add(digits.get(5));
        digitButtonPanel.add(digits.get(6));
        digitButtonPanel.add(digits.get(7));
        digitButtonPanel.add(digits.get(8));
        digitButtonPanel.add(digits.get(9));
        digitButtonPanel.add(digits.get(10));
        digitButtonPanel.add(digits.get(11));

        buttonPanel.add(digitButtonPanel);

        // creating operator button panel
        JPanel operatorButtonPanel = new JPanel(new GridLayout(5, 1));
                
        ImageIcon addIcon = new ImageIcon("./addIcon.png");
        plusSign = new JButton(addIcon);
        
        ImageIcon subtractIcon = new ImageIcon("./minusIcon.png");
        minusSign = new JButton(subtractIcon);
        
        ImageIcon multIcon = new ImageIcon("./multIcon.png");
        multSign = new JButton(multIcon);

        ImageIcon divIcon = new ImageIcon("./divIcon.png");
        divSign = new JButton(divIcon);

        ImageIcon eqIcon = new ImageIcon("./equIcon.png");
        eqSign = new JButton(eqIcon);

        operatorButtonPanel.add(plusSign);
        operatorButtonPanel.add(minusSign);
        operatorButtonPanel.add(multSign);
        operatorButtonPanel.add(divSign);
        operatorButtonPanel.add(eqSign);

        buttonPanel.add(operatorButtonPanel);
        displayPanel.add(buttonPanel);

        digits.get(11).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //currentOperation = "";
                //firstOperand = 0.0;
                //outputField.setText("0");
                resetValues();
            }
        });
        digits.get(10).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String currentText = outputField.getText();
                if(currentText.indexOf(".") < 0){
                    outputField.setText(currentText+".");
                }
            }
        });
        
        OperatorListener opListener = new OperatorListener();
        plusSign.addActionListener(opListener);
        minusSign.addActionListener(opListener);
        multSign.addActionListener(opListener);
        divSign.addActionListener(opListener);
        
        for(int i = 0; i <= 9; i ++){
            digits.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String currentText = outputField.getText();
                    JButton source = (JButton)event.getSource();
                    String newDigit = "";
                    if (source == digits.get(0)) {
                        newDigit = "0";
                    } else if (source == digits.get(1)) {
                        newDigit = "1";
                    } else if (source == digits.get(2)) {
                        newDigit = "2";
                    } else if (source == digits.get(3)) {
                        newDigit = "3";
                    } else if (source == digits.get(4)) {
                        newDigit = "4";
                    } else if (source == digits.get(5)) {
                        newDigit = "5";
                    } else if (source == digits.get(6)) {
                        newDigit = "6";
                    } else if (source == digits.get(7)) {
                        newDigit = "7";
                    } else if (source == digits.get(8)) {
                        newDigit = "8";
                    } else if (source == digits.get(9)) {
                        newDigit = "9";
                    }
                    
                    currentText = currentText + newDigit;
                    currentText = currentText.replaceFirst("^0+(?!$)", "");
                    outputField.setText(currentText);
                }
            });
        }
        
        eqSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Double result = 0.0;
                String currentText = outputField.getText();
                try{
                    Double secondOperand = new Double(currentText);
                    if(currentOperation == "+"){
                        result = firstOperand + secondOperand;
                    } else if(currentOperation == "-"){
                        result = firstOperand - secondOperand;
                    } else if(currentOperation == "*"){
                        result = firstOperand * secondOperand;
                    } else if(currentOperation == "/"){
                        if(secondOperand != 0.0){
                            result = firstOperand / secondOperand;
                        } else {
                            resetValues();
                            outputField.setBackground(Color.PINK);
                        }
                    }
                    outputField.setText(result.toString());
                    firstOperand = result;
                } catch(NumberFormatException e){
                    resetValues();
                }
            }
        });
        add(displayPanel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }

        });

        this.setTitle("Calculator");
        this.setSize(610, 240);
        this.setVisible(true);


        // creating menu bar and menu items
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Operations");
        
        JMenuItem addMenuItem = new JMenuItem("Add");
        addMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentOperation = "+";
                setText();
            }
            
        });
        menu.add(addMenuItem);

        JMenuItem subtractMenuItem = new JMenuItem("Subtract");
        subtractMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentOperation = "-";
                setText();
            }
            
        });
        menu.add(subtractMenuItem);

        JMenuItem multiplyMenuItem = new JMenuItem("Multiply");
        multiplyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentOperation = "*";
                setText();
            }
            
        });
        menu.add(multiplyMenuItem);

        JMenuItem divideMenuItem = new JMenuItem("Divide");
        divideMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentOperation = "/";
                setText();
            }
            
        });
        menu.add(divideMenuItem);
        
        JMenuItem clearMenuItem = new JMenuItem("All Clear");
        clearMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                resetValues();
            }
            
        });
        menu.add(clearMenuItem);

        menuBar.add(menu);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    
    }
    
    /**
     * Sets text to 0 before accepting second value
     */
    private void setText() {
        String currentText = outputField.getText();
        try {
            Double currentTextDouble = new Double(currentText);
            firstOperand = currentTextDouble;
            outputField.setText("0");
        } catch(NumberFormatException e){
            resetValues();              
        }
    }

    /**
     * Resets all values before performing another operation
     */
    private void resetValues(){
        currentOperation = "";
        firstOperand = 0.0;
        outputField.setText("0");
        outputField.setBackground(Color.WHITE);
    }

    /**
     * Class that finds the operator for the operation to complete using an ActionListner interface
     */
    private class OperatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton)event.getSource();
            if (source == plusSign) {
                currentOperation = "+";
            } else if (source == minusSign) {
                currentOperation = "-";
            } else if (source == multSign) {
                currentOperation = "*";
            } else if (source == divSign) {
                currentOperation = "/";
            }
            setText();
        }
    }

    public static void main(String[] args)
    {
        new Calculator();
    }
    
}



