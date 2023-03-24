import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportImage {
    public static void export(BufferedImage image, String folderName){
        File outputFile = new File("./" + folderName + "/" + System.currentTimeMillis() + ".png");
        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to file: " + outputFile.getPath());
        } catch (IOException ex) {
            System.err.println("Error exporting image: " + ex.getMessage());
        }
    }
}
