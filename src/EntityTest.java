import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EntityTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Entity Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600); // Set your desired frame size
            frame.add(new EntityPanel());
            frame.setLocationRelativeTo(null); // Center the JFrame to the screen
            frame.setVisible(true);
        });
    }

    static class EntityPanel extends JPanel {
        private ImageIcon imageIcon;
        private int x, y;
        private Timer timer;

        public EntityPanel() {
            imageIcon = new ImageIcon("images/basicdarius.png"); // Make sure the image is in the same directory as your
                                                                 // Java file

            // Set the preferred size of the panel
            setPreferredSize(new Dimension(800, 600)); // Adjust the size as needed

            // Initialize the timer
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Move the image to a new random position
                    x = new Random().nextInt(getWidth() - 100);
                    y = new Random().nextInt(getHeight() - 100);
                    repaint(); // Repaint the panel to show the updated image position
                }
            });
            timer.setRepeats(true);
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the image at the current coordinates (x, y) with a size of 100x100
            g.drawImage(imageIcon.getImage(), x, y, 100, 100, this);
        }
    }
}
