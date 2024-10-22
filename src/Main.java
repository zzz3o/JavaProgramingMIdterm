import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Font pixelFont;

    Main() {
        setTitle("Pixel Calculator");

        // 네오둠 폰트(또는 유사 픽셀 폰트 사용 가능)
        pixelFont = new Font("Courier", Font.PLAIN, 25);

        setLayout(new BorderLayout());
        createDisplay();
        createKeypad();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);  // 크기를 적당히 조정
        setVisible(true);
    }

    void createDisplay() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea(1, 8);
        displayArea.setText(" 0");
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(175,190,255));
        displayArea.setForeground(new Color(96,120,206));
        displayArea.setFont(pixelFont);
        displayArea.setMargin(new Insets(10, 280, 10, 0));

        displayPanel.add(displayArea, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
    }

    void createKeypad() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(5, 4, 20, 20)); // 그리드 레이아웃 설정

        // 버튼 레이블 설정
        String[] buttons = {
                "C", "+/-", "%", "←", "7", "8", "9", "/",
                "4", "5", "6", "x", "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        // 버튼 스타일 설정
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(pixelFont);   // 픽셀 스타일의 폰트 적용
            button.setFocusPainted(false);
            button.setBackground(new Color(219,219,219));  // 배경색 설정
            button.setForeground(new Color(113,113,113));
            button.setPreferredSize(new Dimension(50, 50));  // 버튼 크기
            button.setBorder(BorderFactory.createLineBorder(new Color(153,153,153), 0));

            // 버튼을 키패드 패널에 추가
            keypadPanel.add(button);
        }

        add(keypadPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new Main();
    }
}
