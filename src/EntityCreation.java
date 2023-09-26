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
    private ImageIcon entityIcon; // Stores the entity image icon
    private int x, y;
    private boolean entityVisible = true;
    private static final Random random = new Random();

    public EntityPanel() {
        // Load the entity icon
        entityIcon = new ImageIcon("images/basicdarius.png");

        // Check if the entity icon was loaded successfully
        if (entityIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.err.println("Error loading entity icon.");
            entityIcon = null; // Set to null to avoid potential NPE
        }

        // Initialize the timer for entity animation
        timer.start();

        // Add a mouse listener to handle entity clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (entityVisible && isClickInsideEntity(e.getX(), e.getY())) {
                    SFX.playDeathSound();
                    entityVisible = false;
                    repaint();
                    PlayPanel.incrementRobberiesCount();
                }
            }
        });
    }

    private Timer timer = new Timer(800, e -> {
        if (entityVisible) {
            generateRandomCoordinates();
            repaint(); // Repaint the panel to show the updated entity position
            SFX.playSecretSound(); // Play a sound when the entity is visible
        }
    });

    private void generateRandomCoordinates() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        x = panelWidth > 0 ? random.nextInt(panelWidth - 140) + 20 : 0;
        y = panelHeight > 0 ? random.nextInt(panelHeight - 140) + 20 : 0;
    }

    private boolean isClickInsideEntity(int clickX, int clickY) {
        // Check if the click is inside the entity's bounding box
        return entityVisible && clickX >= x && clickX <= x + 100 && clickY >= y && clickY <= y + 100;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (entityVisible && entityIcon != null) {
            if (x == 0 && y == 0) {
                generateRandomCoordinates();
            }
            // Draw the entity image at the current coordinates (x, y) with a size of
            // 100x100
            g.drawImage(entityIcon.getImage(), x, y, 100, 100, this);
        }
    }
}
