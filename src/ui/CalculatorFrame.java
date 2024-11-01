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

        /* 폰트 설정 (https://lrl.kr/GLSu에서 참고) */
        try {
            font = new Font("Courier", Font.BOLD, 30);
        } catch (Exception e) {
            font = new JLabel().getFont(); // 폰트를 불러오지 못할 경우 기본 폰트 사용
        }


        setLayout(new BorderLayout());

        display();
        keypad();

        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);
        setVisible(true);
    }

    /**
     * 계산기 디스플레이 패널을 설정하는 메서드입니다.
     *
     * @author Seo Woojin (zzzeow3@gmail.com)
     *
     * @created 2024-10-23
     * @lastModified 2024-10-30
     *
     * @changelog
     * <ul>
     *   <li>2024-10-21: 최초 생성 </li>
     *   <li>2023-10-29: TextArea를 TextField로 수정</li>
     * </ul>
     */

    private void display() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        JPanel paddedDisplayPanel = new JPanel();
        paddedDisplayPanel.setLayout(new BorderLayout());
        paddedDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));  // 여백 추가

        // 각 버튼을 생성하고 스타일을 설정
        displayField = new RoundedTextField(4, 8);
        displayField.setText("0");
        displayField.setEditable(true);
        displayField.setBackground(new Color(187, 191, 202));  // 배경색 설정
        displayField.setForeground(Color.WHITE);
        displayField.setFont(font);
        displayField.setPreferredSize(new Dimension(270, 110));  // 글자색 설정
        displayField.setMargin(new Insets(10, 10, 10, 10));  // 내부 여백 추가

        paddedDisplayPanel.add(displayField, BorderLayout.CENTER);
        displayPanel.add(paddedDisplayPanel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
    }

    /**
     * 계산기 키패드 패널을 설정하는 메서드입니다.
     * 각 버튼을 그리드 레이아웃에 추가하고, 둥근 모서리와 스타일을 적용합니다.
     *
     * @author Seo Woojin (zzzeow3@gmail.com)
     *
     * @created 2024-10-23
     * @lastModified 2024-10-30
     *
     * @changelog
     * <ul>
     *   <li>2024-10-23: 최초 생성 </li>
     *   <li>2023-10-27: 버튼 모양 수정</li>
     * </ul>
     */
    private void keypad() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setLayout(new GridLayout(5, 4, 5, 5)); // 그리드 레이아웃 설정

        // 좌우 여백 추가
        JPanel paddedKeypadPanel = new JPanel(new BorderLayout());
        paddedKeypadPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        String[] buttons = {
                "C", "()", "%", "÷", "7", "8", "9", "x",
                "4", "5", "6", "-", "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        // 각 버튼을 생성하고 스타일을 설정
        for (String text : buttons) {
            JButton button = new RoundedButton(text);
            button.setFont(font);
            button.setFocusPainted(false);
            button.setBackground(new Color(187, 191, 202));  // 배경색 설정
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(50, 50));  // 버튼 크기 설정
            button.setBorder(BorderFactory.createLineBorder(new Color(187, 191, 202), 0));

            button.addActionListener(new ButtonClickListener(button));

            keypadPanel.add(button);
        }

        paddedKeypadPanel.add(keypadPanel, BorderLayout.CENTER);
        add(paddedKeypadPanel, BorderLayout.CENTER);
    }

    /**
     * 버튼 클릭 이벤트 리스너입니다.
     *
     * @author Seo Woojin (zzzeow3@gmail.com)
     *
     * @created 2024-10-30
     * @lastModified 2024-11-01
     *
     * @changelog
     * <ul>
     *   <li>2024-10-30: 최초 생성 </li>
     *   <li>2024-11-01: 숫자 사라짐 오류 수정 </li>
     *
     *   연속 연산 기능은 chat gpt를 참고하였음.
     * </ul>
     */
    private class ButtonClickListener implements ActionListener {
        private JButton button;
        private boolean lastInputWasOperator = false; // 마지막 입력이 연산자인지 추적
        private boolean calculationComplete = false; // 계산이 완료되었는지 추적

        public ButtonClickListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = button.getText();

            // "C" 버튼 클릭 시 텍스트 필드 초기화
            if (buttonText.equals("C")) {
                displayField.setText("0");
                lastInputWasOperator = false;
                calculationComplete = false; // 새 계산이 시작됨을 알림
            } else if ("0123456789".contains(buttonText)) {
                // 숫자를 입력할 경우
                if (calculationComplete) {
                    displayField.setText(buttonText); // 새로운 숫자 입력으로 초기화
                    calculationComplete = false;
                } else if (displayField.getText().equals("0")) {
                    displayField.setText(buttonText); // 0을 새로운 숫자로 대체
                } else {
                    displayField.setText(displayField.getText() + buttonText); // 기존 숫자에 추가
                }
                lastInputWasOperator = false; // 마지막 입력을 숫자로 설정
            } else if ("+-x÷".contains(buttonText)) {
                // 연산자를 입력할 경우
                if (lastInputWasOperator) {
                    // 연산자 대체
                    String currentText = displayField.getText();
                    if (currentText.length() > 0) {
                        currentText = currentText.substring(0, currentText.length() ) + buttonText; // 기존 연산자를 대체
                        displayField.setText(currentText);
                    }
                } else {
                    // 연산자 추가
                    displayField.setText(displayField.getText() + buttonText);
                }
                lastInputWasOperator = true;
                calculationComplete = false;
            } else if (buttonText.equals("=")) {
                // "=" 버튼 클릭 시 계산 수행
                String result = calculate(displayField.getText());
                displayField.setText(result);
                lastInputWasOperator = false; // 계산 후 플래그 초기화
                calculationComplete = true; // 계산 완료 상태
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

    /**
     * 주어진 수식을 계산하는 메서드입니다.
     *
     * @author Seo Woojin (zzzeow3@gmail.com)
     *
     * @created 2024-10-30
     * @lastModified 2024-11-01
     *
     * @param expression 계산할 수식
     * @return 계산 결과 또는 오류 시 "Error" 문자열 반환
     *
     * @changelog
     * <ul>
     *   <li>2024-10-30: 최초 생성 </li>
     *   <li>2023-10-31: 결과값 int로 수정</li>
     *   연산 기능은 chat gpt를 참고하였음.
     * </ul>
     */
    private String calculate(final String expression) {
        // 특수 문자를 처리하고 연산자와 숫자를 분리
        String processedExpression = expression.replace("x", "*").replace("÷", "/");
        try {
            int result = new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < processedExpression.length()) ? processedExpression.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                int parse() { // 결과를 int로 변경
                    nextChar();
                    int x = parseExpression(); // 결과를 int로 가져옴
                    if (pos < processedExpression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                }

                int parseExpression() {
                    int x = parseTerm(); // int로 변경
                    for (;;) {
                        if (eat('+')) x += parseTerm();
                        else if (eat('-')) x -= parseTerm();
                        else return x;
                    }
                }

                int parseTerm() {
                    int x = parseFactor(); // int로 변경
                    for (;;) {
                        if (eat('*')) x *= parseFactor(); // 곱하기
                        else if (eat('/')) {
                            int divisor = parseFactor(); // 나누기
                            if (divisor == 0) throw new ArithmeticException("Division by zero"); // 0으로 나누기 체크
                            x /= divisor; // 나누기 결과도 int로
                        } else return x;
                    }
                }

                int parseFactor() {
                    if (eat('+')) return parseFactor();
                    if (eat('-')) return -parseFactor();

                    int x;
                    int startPos = this.pos;
                    if (eat('(')) {
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9')) { // 소수점 제거
                        while ((ch >= '0' && ch <= '9')) nextChar();
                        x = Integer.parseInt(processedExpression.substring(startPos, this.pos)); // int로 변환
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }

                    return x; // int로 리턴
                }
            }.parse();
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
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
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10);
            g2.setColor(getBackground());
            g2.fill(rounded);

            g2.setColor(getForeground());
            g2.drawString(getText(), getWidth() / 2 - g.getFontMetrics().stringWidth(getText()) / 2, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 3);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * 계산기 디스플레이 모양 설정 클래스입니다.
     * JTextField의 스타일을 둥글게 만들고 커서를 보이지 않게 합니다.
     *
     * @author Seo Woojin (zzzeow3@gmail.com)
     *
     * @created 2024-10-27
     * @lastModified 2024-11-01
     *
     * @changelog
     * <ul>
     *   <li>2024-10-27: 최초 생성 </li>
     *   <li>2023-11-01: 텍스트 커서 오류 수정</li>
     * </ul>
     */
    private class RoundedTextField extends JTextField {
        /**
         * RoundedTextField 생성자입니다.
         * 기본 설정으로 오른쪽 정렬을 적용하고, 텍스트 커서를 보이지 않게 설정합니다.
         *
         * @param rows 표시할 행 수
         * @param cols 표시할 열 수
         */
        public RoundedTextField(int rows, int cols) {
            setOpaque(false); // 투명 배경 설정
            setHorizontalAlignment(JTextField.RIGHT); // 오른쪽 정렬

            // 커서 모양을 수정하여 보이지 않게 설정
            setCaret(new DefaultCaret() {
                @Override
                public void setVisible(boolean visible) {
                    super.setVisible(false); // 커서를 보이지 않게 설정
                }
            });

        }

        /**
         * 텍스트 필드의 배경을 둥근 모양으로 그립니다.
         *
         * @param g 그래픽 객체
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

            // 필드의 크기와 둥근 정도를 설정한 Shape 객체 생성
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10);

            g2.setColor(getBackground());
            g2.fill(rounded);// 배경을 둥근 형태로 채움

            g2.setColor(getForeground());
            super.paintComponent(g);
        }

        /**
         * 둥근 모서리 테두리를 그립니다.
         *
         * @param g 그래픽 객체
         */
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        }
    }
}
