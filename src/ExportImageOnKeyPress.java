import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ExportImageOnKeyPress implements KeyListener {

    private final BufferedImage image;

    public ExportImageOnKeyPress(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'E' || e.getKeyChar() == 'e') {
            File outputFile = new File("./Pictures/" + System.currentTimeMillis() + ".png");
            try {
                ImageIO.write(image, "png", outputFile);
                System.out.println("Image saved to file: " + outputFile.getPath());
            } catch (IOException ex) {
                System.err.println("Error exporting image: " + ex.getMessage());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
}