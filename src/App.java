import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> new MainMenu());
        } catch (Exception e) {
            // Display a detailed error message if an exception occurs
            String errorMessage = "An error occurred while starting the game:\n" + e.getMessage();
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print the stack trace for debugging purposes
        }
    }
}
