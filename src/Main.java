//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.

//import ui.CalculatorFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @author zzz3o
 *
 */
public class Main extends JFrame {
    Main(){
        setTitle("계산기");

        setLayout(new BorderLayout());
        text();
        keypad();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 500);
        setVisible(true);
    }
    void text(){
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(3,40);
        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setText(" 0");
        textArea.setEditable(true);
        textArea.setForeground(Color.LIGHT_GRAY);

        textPanel.add(scrollPane, BorderLayout.CENTER);
        add(textPanel, BorderLayout.NORTH);
    }
    void keypad() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(6, 4, 3, 3));  // 5x5

        String[] buttons = {
                " % ", "CE", "C","<-", "1/x", "power","sqrt", "/", "7", "8", "9", "x", "4", "5", "6", "-",  "1", "2", "3", "+", "+/-", ".", "0", "="
        };

        int count;
        // for문을 사용하여 버튼 생성 및 패널에 추가
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setBackground(Color.WHITE);

            // 행의 4번째와 5번째 버튼을 빨간색으로 나머지를 파란색으로 설정
            if (i % 5 == 3 || i % 5 == 4) {
                button.setForeground(Color.GRAY);
            } else {
                button.setForeground(Color.BLACK);
            }
            keypadPanel.add(button);
        }

        add(keypadPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        new Main();


    }
}