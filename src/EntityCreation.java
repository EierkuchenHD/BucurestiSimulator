import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EntityCreation {
    public static void createEntityPanel(JPanel parentPanel) {
        EntityPanel entityPanel = new EntityPanel();
        parentPanel.add(entityPanel, BorderLayout.CENTER);
        parentPanel.revalidate(); // Ensure layout updates
    }
}

class EntityPanel extends JPanel {
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
                    // Generate random coordinates
                    x = new Random().nextInt(getWidth() - 140) + 20;
                    y = new Random().nextInt(getHeight() - 140) + 20;
                }
                repaint(); // Repaint the panel to show the updated entity position
            }
        });
        timer.setRepeats(true);
        timer.start();
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
