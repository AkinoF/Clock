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
        setBackground(Color.WHITE); // Устанавливаем цвет фона
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
        int day = now.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) + 1; // Январь = 0
        int year = now.get(Calendar.YEAR); // Получаем год

        // Устанавливаем размеры и центр
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3; // Уменьшаем радиус

        // Рисуем овал часов
        g.setColor(Color.WHITE);
        g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // Рисуем деления и циферблат с римскими цифрами
        String[] romanNumerals = {"III", "II", "I", "XII", "XI", "X", "IX", "VIII", "VII", "VI", "V", "IV"};
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int x1 = centerX + (int) (Math.cos(angle) * (radius - 10));
            int y1 = centerY - (int) (Math.sin(angle) * (radius - 10));
            int x2 = centerX + (int) (Math.cos(angle) * radius);
            int y2 = centerY - (int) (Math.sin(angle) * radius);
            g.drawLine(x1, y1, x2, y2);

            // Рисуем римские цифры за часами
            String hourString = romanNumerals[i];
            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(hourString);
            int textHeight = metrics.getAscent();
            int textX = centerX + (int) (Math.cos(angle) * (radius + 10)) - textWidth / 2; // Изменено на radius + 10
            int textY = centerY - (int) (Math.sin(angle) * (radius + 10)) + textHeight / 2; // Изменено на radius + 10
            g.drawString(hourString, textX, textY);
        }

        // Рисуем часовую стрелку с треугольником
        double hourAngle = Math.toRadians((hours % 12 + minutes / 60.0) * 180); // Угол для часовой стрелки
        int hourX = centerX + (int) (Math.cos(hourAngle - Math.PI / 2) * (radius * 0.5));
        int hourY = centerY + (int) (Math.sin(hourAngle - Math.PI / 2) * (radius * 0.5));
        g.setColor(Color.BLACK);
        g.drawLine(centerX, centerY, hourX, hourY);

        // Рисуем треугольник на конце часовой стрелки
        int hourTriangleOffset = 10; // Смещение треугольника
        int[] xPointsHour = {
                hourX,
                hourX - (int)(15 * Math.cos(hourAngle + Math.PI / 2)), // Увеличиваем длину
                hourX + (int)(15 * Math.cos(hourAngle - Math.PI / 2))  // Увеличиваем длину
        };
        int[] yPointsHour = {
                hourY,
                hourY - (int)(15 * Math.sin(hourAngle + Math.PI / 2)), // Увеличиваем длину
                hourY - (int)(15 * Math.sin(hourAngle - Math.PI / 2))  // Увеличиваем длину
        };
        g.fillPolygon(xPointsHour, yPointsHour, 3);

        // Рисуем минутную стрелку с кругом на конце
        double minuteAngle = Math.toRadians((minutes + seconds / 60.0) * 6);
        int minuteX = centerX + (int) (Math.cos(minuteAngle - Math.PI / 2) * (radius * 0.7));
        int minuteY = centerY + (int) (Math.sin(minuteAngle - Math.PI / 2) * (radius * 0.7));
        g.setColor(Color.BLACK);
        g.drawLine(centerX, centerY, minuteX, minuteY);

        // Рисуем круг на конце минутной стрелки
        g.setColor(Color.BLUE);
        g.fillOval(minuteX - 8, minuteY - 8, 16, 16); // Круг на конце минутной стрелки

        // Рисуем секундную стрелку с треугольником
        double secondAngle = Math.toRadians(seconds * 6);
        int secondX = centerX + (int) (Math.cos(secondAngle - Math.PI / 2) * (radius * 0.9));
        int secondY = centerY + (int) (Math.sin(secondAngle - Math.PI / 2) * (radius * 0.9));
        g.setColor(Color.RED);
        g.drawLine(centerX, centerY, secondX, secondY);

        // Рисуем треугольник на конце секундной стрелки
        int[] xPointsSecond = {
                secondX,
                secondX - (int)(15 * Math.cos(secondAngle + Math.PI / 3)), // Увеличиваем размер
                secondX + (int)(15 * Math.cos(secondAngle - Math.PI / 3))  // Увеличиваем размер
        };
        int[] yPointsSecond = {
                secondY,
                secondY - (int)(15 * Math.sin(secondAngle + Math.PI / 3)), // Увеличиваем размер
                secondY - (int)(15 * Math.sin(secondAngle - Math.PI / 3))  // Увеличиваем размер
        };
        g.fillPolygon(xPointsSecond, yPointsSecond, 3);

        // Отображаем год слева от часов
        String yearString = String.valueOf(year);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(yearString, centerX - radius - g.getFontMetrics().stringWidth(yearString) - 10, centerY - 20); // Год слева от часов

        // Отображаем дату (день и месяц) справа от часов
        String dateString = String.format("%02d/%02d", day, month);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(dateString, centerX + radius + 10, centerY - 20); // Дата справа от часов

        // Рисуем два зеленых кружка с жирной линией под овалом
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3)); // Устанавливаем толщину линии
        g.setColor(Color.GREEN);
        g.fillOval(centerX - 100, centerY + radius + 10, 20, 20); // Первый кружок
        g.fillOval(centerX + 80, centerY + radius + 10, 20, 20); // Второй кружок
        g.setColor(Color.BLACK);
        g.drawOval(centerX - 100, centerY + radius + 10, 20, 20); // Обводим первый кружок
        g.drawOval(centerX + 80, centerY + radius + 10, 20, 20); // Обводим второй кружок

        // Рисуем овал вокруг часов с жирной линией
        g.setColor(Color.BLACK);
        g.drawOval(centerX - radius - 55, centerY - radius - 30, (radius + 55) * 2, (radius + 30) * 2); // Овал вокруг часов
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