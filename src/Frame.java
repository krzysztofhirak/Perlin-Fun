import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {
    private Canvas canvas;
    Dimension canvasSize = new Dimension(800, 800);
    Dimension imageSize = new Dimension(1600, 1600);

    public Frame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Fun with Perlin Noise :)");

//        canvas = new Canvas(canvasSize);
        canvas = new Canvas(canvasSize, imageSize);
        canvas.setPreferredSize(canvasSize);
        add(canvas);
        addKeyListener(this);
        pack();
        setVisible(true);
        setResizable(false);
    }

    @Override public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'R') {
            canvas = new Canvas(canvasSize);
            getContentPane().removeAll();
            getContentPane().add(canvas);
            getContentPane().revalidate();
            System.out.println("Generated new image");
        }
    }

    @Override public void keyPressed(KeyEvent e) {

    }

    @Override public void keyReleased(KeyEvent e) {

    }
}
