package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CalculatorFrame extends JFrame {

    private Font pixelFont;

    public CalculatorFrame() {
        setTitle("Calculator");

        // 폰트 초기화
        pixelFont = new Font("SansSerif", Font.PLAIN, 20); // 기본 폰트 사용

        setLayout(new BorderLayout());

        // UI 설정
        display();
        keypad();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);
        setVisible(true);
    }

    private void display() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        // 텍스트 영역의 위 아래에 여백
        JPanel paddedDisplayPanel = new JPanel();
        paddedDisplayPanel.setLayout(new BorderLayout());
        paddedDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));  // 여백 추가

        // 둥근 모서리의 JTextArea 생성
        JTextArea displayArea = new RoundedTextArea(4, 8);
        displayArea.setText("0");
        displayArea.setEditable(true);
        displayArea.setBackground(new Color(219, 219, 219));
        displayArea.setForeground(new Color(113, 113, 113));
        displayArea.setFont(pixelFont);
        displayArea.setMargin(new Insets(10, 10, 10, 10)); // 내부 여백 추가

        // 커서 초기 위치를 텍스트의 마지막으로 설정
        displayArea.setCaretPosition(displayArea.getDocument().getLength());

        paddedDisplayPanel.add(displayArea, BorderLayout.CENTER);
        displayPanel.add(paddedDisplayPanel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
    }

    private void keypad() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(5, 4, 10, 20)); // 그리드 레이아웃 설정

        // 좌우 여백을 주기 위해 paddingPanel을 사용
        JPanel paddedKeypadPanel = new JPanel(new BorderLayout());
        paddedKeypadPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));  // 좌우에 20px의 여백 추가

        // 버튼 레이블 설정
        String[] buttons = {
                "C", "()", "%", "÷", "7", "8", "9", "X",
                "4", "5", "6", "-", "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        // 버튼 스타일 설정
        for (String text : buttons) {
            // 둥근 모서리의 버튼 생성
            JButton button = new RoundedButton(text);
            button.setFont(pixelFont);   // 픽셀 스타일의 폰트 적용
            button.setFocusPainted(false);
            button.setBackground(new Color(219, 219, 219));  // 배경색 설정
            button.setForeground(new Color(113, 113, 113));
            button.setPreferredSize(new Dimension(50, 50));  // 버튼 크기
            button.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 0));

            keypadPanel.add(button);
        }

        // 패딩된 패널에 키패드 패널을 추가
        paddedKeypadPanel.add(keypadPanel, BorderLayout.CENTER);
        add(paddedKeypadPanel, BorderLayout.CENTER);
    }

    // 둥근 버튼 클래스
    private class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 둥근 모서리 그리기
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 80, 60);
            g2.setColor(getBackground());
            g2.fill(rounded);

            g2.setColor(getForeground());
            g2.drawString(getText(), getWidth() / 2 - g.getFontMetrics().stringWidth(getText()) / 2, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 3);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 둥근 모서리의 JTextArea 클래스
    private class RoundedTextArea extends JTextArea {
        public RoundedTextArea(int rows, int cols) {
            super(rows, cols);
            setOpaque(false);
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // 오른쪽 정렬
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 둥근 모서리 그리기
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(getBackground());
            g2.fill(rounded);

            g2.setColor(getForeground());
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 둥근 모서리 테두리 그리기
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(getForeground());
            g2.draw(rounded);
            g2.dispose();
        }
    }
}
