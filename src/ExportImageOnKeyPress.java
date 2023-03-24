import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class ExportImageOnKeyPress implements KeyListener {

    private final BufferedImage image;

    public ExportImageOnKeyPress(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'E' || e.getKeyChar() == 'e') {
            ExportImage.export(image, "Pictures");
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