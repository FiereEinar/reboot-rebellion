package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image image;         // The base image of the button
    private float scale = 1.0f;  // Current scale of the button (1.0 = normal size)

    public CustomButton(String imagePath, int width, int height) {
        // Load the button image
        this.image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(imagePath));

        // Set button size
        this.setPreferredSize(new Dimension(width, height));

        // Add mouse listeners for hover and click effects
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scale = 0.95f; // Shrink slightly on hover
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                scale = 1.0f; // Return to normal size
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                scale = 0.9f; // Shrink further on click
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scale = 0.95f; // Return to hover size
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Calculate scaled dimensions
        int scaledWidth = (int) (getWidth() * scale);
        int scaledHeight = (int) (getHeight() * scale);

        // Center the image after scaling
        int x = (getWidth() - scaledWidth) / 2;
        int y = (getHeight() - scaledHeight) / 2;

        // Draw the scaled image
        g2.drawImage(image, x, y, scaledWidth, scaledHeight, this);
    }
}
