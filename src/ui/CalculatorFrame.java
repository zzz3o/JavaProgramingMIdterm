package ui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CalculatorFrame extends JFrame {

    private Font font;
    private JTextField displayField;

    public CalculatorFrame() {
        setTitle("계산기");

        /*@see https://lrl.kr/GLSu
        폰트 설정*/
        try {
            font = new Font("Courier", Font.BOLD, 30);
        } catch (Exception e) {
            font = new JLabel().getFont(); // 폰트를 불러오지 못할 경우 기본 폰트 사용
        }


        setLayout(new BorderLayout());

        display();
        keypad();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);
        setVisible(true);
    }

    private void display() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        JPanel paddedDisplayPanel = new JPanel();
        paddedDisplayPanel.setLayout(new BorderLayout());
        paddedDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));  // 여백 추가

        // 둥근 모서리의 JTextField 생성
        displayField = new RoundedTextField(4, 8);
        displayField.setText("0");
        displayField.setEditable(true);
        displayField.setBackground(new Color(187, 191, 202));
        displayField.setForeground(new Color(232, 232, 232));
        displayField.setFont(font);
        displayField.setPreferredSize(new Dimension(270, 110));
        displayField.setMargin(new Insets(10, 10, 10, 10)); // 내부 여백 추가

        paddedDisplayPanel.add(displayField, BorderLayout.CENTER);
        displayPanel.add(paddedDisplayPanel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
    }

    private void keypad() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(5, 4, 10, 20)); // 그리드 레이아웃 설정

        // 좌우 여백 추가
        JPanel paddedKeypadPanel = new JPanel(new BorderLayout());
        paddedKeypadPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        String[] buttons = {
                "C", "()", "%", "÷", "7", "8", "9", "x",
                "4", "5", "6", "-", "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        // 버튼 스타일 설정
        for (String text : buttons) {
            // 둥근 모서리의 버튼 생성
            JButton button = new RoundedButton(text);
            button.setFont(font);
            button.setFocusPainted(false);
            button.setBackground(new Color(187, 191, 202));  // 배경색 설정
            button.setForeground(new Color(232, 232, 232));
            button.setPreferredSize(new Dimension(50, 50));  // 버튼 크기
            button.setBorder(BorderFactory.createLineBorder(new Color(187, 191, 202), 0));

            button.addActionListener(new ButtonClickListener(button));

            keypadPanel.add(button);
        }

        paddedKeypadPanel.add(keypadPanel, BorderLayout.CENTER);
        add(paddedKeypadPanel, BorderLayout.CENTER);
    }

    // 버튼 클릭 이벤트 리스너
    private class ButtonClickListener implements ActionListener {
        private JButton button;

        public ButtonClickListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String buttonText = button.getText();

            // "C" 버튼 클릭 시 텍스트 필드 초기화
            if (buttonText.equals("C")) {
                displayField.setText("0");
            } else if ("0123456789".contains(buttonText) || "+-x÷".contains(buttonText)) {
                // 숫자 또는 사칙연산 기호를 입력할 경우
                if (displayField.getText().equals("0")) {
                    displayField.setText(buttonText); // 0이 있는 경우 새로운 문자로 대체
                } else {
                    displayField.setText(displayField.getText() + buttonText); // 기존 텍스트에 추가
                }
            }

            // 버튼 색상 변경
            button.setBackground(new Color(73, 84, 100)); // 클릭된 버튼의 색 변경

            Timer timer = new Timer(70, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    button.setBackground(new Color(187, 191, 202)); // 원래 색으로 복원
                }
            });
            timer.setRepeats(false);
            timer.start();


        }
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

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

    // 둥근 모서리의 JTextField 클래스
    private class RoundedTextField extends JTextField {
        public RoundedTextField(int rows, int cols) {
            setOpaque(false);
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // 오른쪽 정렬

            setCaret(new DefaultCaret() {
                @Override
                public void setVisible(boolean visible) {
                    super.setVisible(false); // 커서를 보이지 않게 설정
                }
            });

        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(getBackground());
            g2.fill(rounded);

            g2.setColor(getForeground());
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        }
    }
}
