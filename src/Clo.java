import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class Clo extends JPanel implements ActionListener {
    private Timer timer;

    public Clo() {
        timer = new Timer(1000, this); // Обновление каждую секунду
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClock(g);
    }

    private void drawClock(Graphics g) {
        // Получаем текущее время
        Calendar now = Calendar.getInstance();
        int seconds = now.get(Calendar.SECOND);
        int minutes = now.get(Calendar.MINUTE);
        int hours = now.get(Calendar.HOUR_OF_DAY);

        // Устанавливаем размеры и центр
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 20;

        // Рисуем круг часов
        g.setColor(Color.WHITE);
        g.fillOval(20, 20, width - 40, height - 40);
        g.setColor(Color.BLACK);
        g.drawOval(20, 20, width - 40, height - 40);

        // Рисуем деления
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int x1 = centerX + (int) (Math.cos(angle) * (radius - 10));
            int y1 = centerY - (int) (Math.sin(angle) * (radius - 10));
            int x2 = centerX + (int) (Math.cos(angle) * radius);
            int y2 = centerY - (int) (Math.sin(angle) * radius);
            g.drawLine(x1, y1, x2, y2);
        }

        // Рисуем стрелку часов
        double hourAngle = Math.toRadians((hours % 12 + minutes / 60.0) * 30);
        int hourX = centerX + (int) (Math.cos(hourAngle - Math.PI / 2) * (radius * 0.5));
        int hourY = centerY + (int) (Math.sin(hourAngle - Math.PI / 2) * (radius * 0.5));
        g.setColor(Color.BLACK);
        g.drawLine(centerX, centerY, hourX, hourY);

        // Рисуем стрелку минут
        double minuteAngle = Math.toRadians((minutes + seconds / 60.0) * 6);
        int minuteX = centerX + (int) (Math.cos(minuteAngle - Math.PI / 2) * (radius * 0.7));
        int minuteY = centerY + (int) (Math.sin(minuteAngle - Math.PI / 2) * (radius * 0.7));
        g.setColor(Color.BLUE);
        g.drawLine(centerX, centerY, minuteX, minuteY);

        // Рисуем стрелку секунд
        double secondAngle = Math.toRadians(seconds * 6);
        int secondX = centerX + (int) (Math.cos(secondAngle - Math.PI / 2) * (radius * 0.9));
        int secondY = centerY + (int) (Math.sin(secondAngle - Math.PI / 2) * (radius * 0.9));
        g.setColor(Color.RED);
        g.drawLine(centerX, centerY, secondX, secondY);

        // Отображаем текущее время
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(timeString, centerX - g.getFontMetrics().stringWidth(timeString) / 2, centerY + radius + 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // Перерисовываем панель каждую секунду
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Часы");
        Clo clock = new Clo();
        frame.add(clock);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}