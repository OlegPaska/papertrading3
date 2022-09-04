package GUI;

import javax.swing.*;
import java.awt.*;
import Market.JSONparser;

public class Basic extends JPanel {
    //this is also just for reference
    private JFrame frame;

    public Basic(String ticker) {
        // initialise the window
        frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);

        // create the canvas that will hold the actual graphics
        frame.getContentPane().add(this);

        // display the frame AFTER adding the panel to prevent drawing glitches
        frame.setVisible(true);

        // get market data
        JSONparser jason = new JSONparser();

    }

    @Override
    public void paintComponent(Graphics g) {
        int left = 50;    // hard-coded just for testing
        int top = 80;
        int width = 40;
        int height = 150;
        g.setColor(Color.red);
        g.fillRect(left, top, width, height);
    }
}
