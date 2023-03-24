import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    public double[][] canvas;
    Dimension size;
    BufferedImage image;

    public Canvas(Dimension size){
        this.size = size;
        setSize(size);
        canvas = new double[getWidth()][getHeight()];

//        canvas = Noise(4, 2);
        canvas = MultipleNoises();
        canvas = mapToZeroOne(canvas);
        image = toImage(canvas);

        ExportImageOnKeyPress listener = new ExportImageOnKeyPress(image);
        addKeyListener(listener);
        setFocusable(true);
    }

    private double[][] Noise(int octaves, int persistence){
        double[][] result = new double[getWidth()][getHeight()];
        PerlinNoise.newPermutation();
        PerlinNoise perlin = new PerlinNoise();
        for(int x = 0; x < canvas.length; x++){
            for(int y = 0; y < canvas[0].length; y++){
                result[x][y] = perlin.octavePerlin((double) x/getWidth(), (double) y/getHeight(), 0, octaves, persistence);
            }
        }
        return result;
    }

    private double[][] MultipleNoises(){
        double[][] result = new double[getWidth()][getHeight()];
        result = Noise(2, 2);
        result = add(result, Noise(3, 2));
        result = add(result, Noise(4, 2));
        result = add(result, Noise(5, 2));
//        result = add(result, Noise(6, 2));
        return result;
    }

    private double[][] add(double[][] arr1, double[][] arr2){
        double[][] result = new double[arr1.length][arr1[0].length];
        for(int x = 0; x < arr1.length; x++){
            for(int y = 0; y < arr1[0].length; y++){
                result[x][y] = arr1[x][y] + arr2[x][y];
            }
        }
        return result;
    }

    private BufferedImage toImage(double[][] canvas){
        int width = canvas.length;
        int height = canvas[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                image.setRGB(x, y, mapDoubleToColorBW(canvas[x][y]).getRGB());
            }
        }
        return image;
    }

    private Color mapDoubleToColorBW(double d){
        int value = (int) (d * 255);
        return new Color(value, value, value);
    }

    private double[] maxWithPos(double[][] d){
        double max = Double.NEGATIVE_INFINITY;
        double[] out = new double[3];
        out[2] = max;
        for(int x = 0; x < d.length; x++){
            for(int y = 0; y < d[0].length; y++){
                if (d[x][y] > max) {
                    max = d[x][y];
                    out[0] = x;
                    out[1] = y;
                    out[2] = max;
                }
            }
        }
        return out;
    }

    private double[] minWithPos(double[][] d){
        double min = Double.POSITIVE_INFINITY;
        double[] out = new double[3];
        out[2] = min;
        for(int x = 0; x < d.length; x++){
            for(int y = 0; y < d[0].length; y++){
                if (d[x][y] < min) {
                    min = d[x][y];
                    out[0] = x;
                    out[1] = y;
                    out[2] = min;
                }
            }
        }
        return out;
    }

    private double[][] mapToZeroOne(double[][] d){
        double min = minWithPos(d)[2];
        double max = maxWithPos(d)[2];

        double range = max - min;
        double[][] mappedArr = new double[d.length][d[0].length];

        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                double val = d[i][j];
                double mappedVal = (val - min) / range;
                mappedArr[i][j] = mappedVal;
            }
        }

        return mappedArr;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enter noise function
//        DefaultPerlinNoise();
//        IsohipsNoise();
//        RainbowNoise();
        Noise2();

        g.drawImage(image, 0, 0, null);
    }

    private void DefaultPerlinNoise(Graphics g){
//        g.drawImage(image, 0, 0, null);
        g.setColor(Color.RED);
        int spaces = 8;
        for(int x = 0; x < canvas.length; x = x + spaces){
            for(int y = 0; y < canvas[0].length; y = y + spaces){
                int length = 8;
//                g.drawLine(x, y, x + (int)getCirclePosition(canvas[x][y], length)[0], y + (int)getCirclePosition(canvas[x][y], length)[1]);
            }
        }
    }

    private void Noise1(){
        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                image.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

        int[][] amount = new int[getWidth()][getHeight()];
        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                amount[x][y] = 255;
            }
        }

        for(int x = 0; x < getWidth(); x += 1){
            for(int y = 0; y < getHeight(); y += 1){
                if(inBounds(x, y)){
                    double[] first = {x, y, canvas[x][y]};
                    double[] next = null;
                    for(int i = 0; i < 1000; i++){
                        if(i == 0) next = nextHigher(first);
                        else {
                            next = nextHigher(next);
                            if(next[2] == Double.MIN_VALUE) break;
                        }
                        amount[x][y]--;
//                        g.drawRect((int) next[0], (int) next[1], 1, 1);
                    }
                }
            }
        }

        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if(amount[x][y] < 0) amount[x][y] = 0;
                image.setRGB(x, y, new Color(amount[x][y], 0, 0).getRGB());
            }
        }
    }

    private void Noise2(){
        int[][] values = new int[getWidth()][getHeight()];

        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                values[x][y] = 255;
            }
        }

        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                double[] next = new double[3];
                next[0] = x; next[1] = y; next[2] = canvas[x][y];
                for(int i = 0; i < 255; i++){
//                    System.out.println(next[0] + " " + next[1] + " " + next[2]);
                    next = nextPixel(next);
                    if(next[0] != -1 && next[1] != -1 && values[(int) next[0]][(int) next[1]] > 0){
                        values[(int) next[0]][(int) next[1]]--;
                    }
                }
            }
        }

        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                Color color = new Color(values[x][y], values[x][y], values[x][y]);
                image.setRGB(x, y, color.getRGB());
            }
        }

        System.out.println("Done.");
    }

    private void IsohipsNoise(){
        for(int x = 0; x < getWidth(); x++){
            for(int y = 0; y < getHeight(); y++){
                if((int)(canvas[x][y] * 10) % 2 == 0){
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } else image.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
    }

    private void RainbowNoise(){
        for(int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int rgb = Color.HSBtoRGB((float) canvas[x][y], 1, 1);
                image.setRGB(x, y, rgb);
            }
        }
    }

    private double[] nextPixel(double[] pixel){
        double[] out = new double[3];
        int x = (int) pixel[0];
        int y = (int) pixel[1];
        if(pixel[2] <= 0.0625 || pixel[2] > 0.9375) { if(inBounds(x, y-1)) { out[0]=x; out[1]=y-1; out[2] = canvas[x][y-1]; } }
        else if(pixel[2] <= 0.1875) { if(inBounds(x+1, y-1)) { out[0]=x+1; out[1]=y-1; out[2] = canvas[x+1][y-1]; } }
        else if(pixel[2] <= 0.3125) { if(inBounds(x+1, y)) { out[0]=x+1; out[1]=y; out[2] = canvas[x+1][y]; } }
        else if(pixel[2] <= 0.4375) { if(inBounds(x+1, y+1)) { out[0]=x+1; out[1]=y+1; out[2] = canvas[x+1][y+1]; } }
        else if(pixel[2] <= 0.5625) { if(inBounds(x, y+1)) { out[0]=x; out[1]=y+1; out[2] = canvas[x][y+1]; } }
        else if(pixel[2] <= 0.6875) { if(inBounds(x-1, y+1)) { out[0]=x-1; out[1]=y+1; out[2] = canvas[x-1][y+1]; } }
        else if(pixel[2] <= 0.8125) { if(inBounds(x-1, y)) { out[0]=x-1; out[1]=y; out[2] = canvas[x-1][y]; } }
        else if(pixel[2] <= 0.9375) { if(inBounds(x-1, y-1)) { out[0]=x-1; out[1]=y-1; out[2] = canvas[x-1][y-1]; } }
//        else out[0] = -1; out[1] = -1; out[2] = -1;
//        System.out.println(out[0] + " " + out[1] + " " + out[2]);
        return out;
    }

    private double[] nextHigher(double[] max){
        double[] next = new double[3];
        next[2] = Double.MIN_VALUE;
        for(int x = (int) (max[0]-1); x <= max[0]+1; x++){
            for(int y = (int) (max[1]-1); y <= max[1]+1; y++){
                if(x != max[0] && y != max[1] && inBounds(x, y)){
                    if(canvas[x][y] > next[2] && canvas[x][y] < max[2]){
                        next[0] = x;
                        next[1] = y;
                        next[2] = canvas[x][y];
                    }
                }
            }
        }
        return next;
    }

    private boolean inBounds(int x, int y){
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    private static double[] getCirclePosition(double angle, int length) {
        double[] position = new double[2];
        double theta = angle * 2 * Math.PI;
        position[0] = length * Math.cos(theta);
        position[1] = length * Math.sin(theta);
        return position;
    }
}
