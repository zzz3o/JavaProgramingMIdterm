import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Main extends JFrame {
    private Font pixelFont;

    Main() {
        setTitle("Pixel Calculator");

        // 네오둠 폰트(또는 유사 픽셀 폰트 사용 가능)
        pixelFont = new Font("Courier", Font.PLAIN, 25);

        setLayout(new BorderLayout());
        display();
        keypad();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);  // 크기를 적당히 조정
        setVisible(true);
    }

    void display() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        // 텍스트 영역의 위 아래에 여백
        JPanel paddedDisplayPanel = new JPanel();
        paddedDisplayPanel.setLayout(new BorderLayout());
        paddedDisplayPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  //  여백 추가

        // 둥근 모서리의 JTextArea 생성
        JTextArea displayArea = new RoundedTextArea(1, 8);
        displayArea.setText("0");
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(175, 190, 255));
        displayArea.setForeground(new Color(96, 120, 206));
        displayArea.setFont(pixelFont);
        displayArea.setMargin(new Insets(10, 10, 10, 10)); // 내부 여백 추가

        paddedDisplayPanel.add(displayArea, BorderLayout.CENTER);
        displayPanel.add(paddedDisplayPanel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);
    }

    void keypad() {
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

        add(keypadPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new Main();
    }

    // 둥근 버튼 클래스
    class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 둥근 모서리 그리기
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
            g2.setColor(getBackground());
            g2.fill(rounded);

            g2.setColor(getForeground());
            g2.drawString(getText(), getWidth() / 2 - g.getFontMetrics().stringWidth(getText()) / 2, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 3);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 둥근 모서리의 JTextArea 클래스
    class RoundedTextArea extends JTextArea {
        public RoundedTextArea(int rows, int cols) {
            super(rows, cols);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 둥근 모서리 그리기
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
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
            Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
            g2.setColor(getForeground());
            g2.draw(rounded);
            g2.dispose();
        }
    }
}
