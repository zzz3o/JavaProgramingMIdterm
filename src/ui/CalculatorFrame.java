package ui;

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame extends JFrame {

    private JPanel panel;

    public CalculatorFrame(){

        setTitle("계산기");

        south();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 500);
        setVisible(true);

    }


    void south(){
        JPanel panel = new JPanel(new GridLayout(3,4));
        JButton button1 = new JButton("1");
        JButton button2 = new JButton("2");
        JButton button3 = new JButton("3");
        JButton button4 = new JButton("4");
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        add(panel);


    }

}
