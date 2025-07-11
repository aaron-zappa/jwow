import javax.swing.*;
import java.awt.*;

public class RedLineDrawer extends JFrame {

    public RedLineDrawer() {
        setTitle("Red Line Drawer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel);
    }

    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.drawLine(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RedLineDrawer app = new RedLineDrawer();
            app.setVisible(true);
        });
    }
}