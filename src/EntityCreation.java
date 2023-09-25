import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class EntityCreation {
    public static void createEntityPanel(JPanel parentPanel) {
        EntityPanel entityPanel = new EntityPanel();
        parentPanel.add(entityPanel, BorderLayout.CENTER);
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
        timer = new Timer(1000, e -> {
            if (entityVisible) {
                SFX.playSecretSound();
                generateRandomCoordinates();
            }
            repaint(); // Repaint the panel to show the updated entity position
        });
        timer.setRepeats(true);
        timer.start();

        // Add a MouseListener to handle image click events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (entityVisible && isClickInsideEntity(e.getX(), e.getY())) {
                    entityVisible = false;
                    repaint(); // Repaint to hide the entity
                }
            }
        });
    }

    private void generateRandomCoordinates() {
        x = new Random().nextInt(getWidth() - 140) + 20;
        y = new Random().nextInt(getHeight() - 140) + 20;
    }

    private boolean isClickInsideEntity(int clickX, int clickY) {
        return clickX >= x && clickX <= x + 100 && clickY >= y && clickY <= y + 100;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (entityVisible) {
            if (x == 0 && y == 0) {
                generateRandomCoordinates();
            }
            // Draw the image at the current coordinates (x, y) with a size of 100x100
            g.drawImage(entityIcon.getImage(), x, y, 100, 100, this);
        }
    }
}
