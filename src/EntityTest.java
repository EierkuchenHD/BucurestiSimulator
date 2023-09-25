import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class EntityTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bucuresti Simulator | Entity Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 720); // Set your desired frame size
            frame.setMinimumSize(new Dimension(600, 600));
            frame.add(new EntityPanel());
            frame.setLocationRelativeTo(null); // Center the JFrame to the screen
            frame.setVisible(true);
        });
    }

    static class EntityPanel extends JPanel {
        private ImageIcon entityIcon;
        private int x, y;
        private boolean entityVisible = true;
        private Timer timer;

        public EntityPanel() {
            entityIcon = new ImageIcon("images/basicdarius.png");

            // Initialize the timer
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (entityVisible) {
                        SFX.playSecretSound();
                        // Generate random coordinates at least 20 pixels away from the panel borders
                        x = new Random().nextInt(getWidth() - 140) + 20;
                        y = new Random().nextInt(getHeight() - 140) + 20;
                    }
                    repaint(); // Repaint the panel to show the updated entity position
                }
            });
            timer.setRepeats(true);
            timer.start();

            // Add a mouse listener to handle click events
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (entityVisible && e.getX() >= x && e.getX() <= x + 100 && e.getY() >= y && e.getY() <= y + 100) {
                        // If the click is within the entity's boundaries, make the entity disappear
                        entityVisible = false;
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Generate random initial coordinates only once when painting for the first
            // time
            if (x == 0 && y == 0) {
                x = new Random().nextInt(getWidth() - 140) + 20;
                y = new Random().nextInt(getHeight() - 140) + 20;
            }

            if (entityVisible) {
                // Draw the image at the current coordinates (x, y) with a size of 100x100
                g.drawImage(entityIcon.getImage(), x, y, 100, 100, this);
            }
        }
    }
}
