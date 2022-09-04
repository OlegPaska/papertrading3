package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import Market.JSONparser;
import Market.Stock;
import jdk.swing.interop.SwingInterOpUtils;

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

        double screenx = 700;
        double screeny = 500;

        JSONparser jason = new JSONparser();
        //{[open],[close],[high],[low]}
        double[][] data = jason.getGraphData("AAPL");
        //getting min and max to scale graph to screen width
        double min = data[3][0];
        double max = data[2][0];
        for(int i = data[2].length-1; i>=data[2].length-51;i--){
            if(data[3][i] < min){
                min = data[3][i];
            }
            if(data[2][i] > max){
                max = data[2][i];
            }
        }

        //System.out.println(Arrays.deepToString(data));

        int count=0;
        for (int i = data[0].length-1; i>=data[0].length-51;i--){

            if (data[0][i] < data[1][i]){
                g.setColor(Color.green);
                double ypos = ((data[1][i]-min)/(max-min));
                //body of candle
                g.fillRect((int)((screenx/50)*count), (int)(ypos*screeny), (int)(screenx/50), (int)((((data[0][i]-min)/(max-min))-ypos)*screeny));
/*                System.out.println("x: " + (int)((screenx/50)*count));
                System.out.println("y: " + (int)(ypos*screeny));
                System.out.println("w: " + (int)(screenx/50));
                System.out.println("h: " + (int)((ypos-((data[0][i]-min)/(max-min)))*screeny));
                System.out.println("------------------------------------------------------");*/
                //upper wick
/*                g.fillRect((int)(((screenx/50)*count)+screenx/100), (int)(((data[2][i]-min)/(max-min))*screeny), 2,(int)((((data[2][i]-min)/(max-min))-(ypos))*screeny));
                System.out.println("x: "+(int)(((screenx/50)*count)+screenx/100));
                System.out.println("y: "+(int)(((data[2][i]-min)/(max-min))*screeny));
                System.out.println("h: "+(int)((((data[2][i]-min)/(max-min))-(ypos))*screeny));
                System.out.println("------------------------------------------------------------");*/

                //lower wick
                //rect height can be negative and it goes up. i am too tired to know what to do with this information
                g.setColor(Color.BLACK);
                g.fillRect((int)(((screenx/50)*count)+screenx/100), (int)(((data[0][i]-min)/(max-min))*screeny), 2, (int)-((((data[3][i]-min)/(max-min))-((data[0][i]-min)/(max-min)))*screeny));
                System.out.println((int)-((((data[3][i]-min)/(max-min))-((data[0][i]-min)/(max-min)))*screeny));


            } else{
                g.setColor(Color.red);
                double ypos = ((data[0][i]-min)/(max-min));
                g.fillRect((int)((screenx/50)*count), (int)(ypos*screeny), (int)(screenx/50), (int)(((ypos-((data[1][i]-min)/(max-min))))*screeny));

            }
            count++;

        }
/*
        int left = 50;    // hard-coded just for testing
        int top = 80;
        int width = 40;
        int height = 150;
        g.setColor(Color.red);
        g.fillRect(left, top, width, height);
        g.fillRect((int)10.5,(int)10.3,(int) screenx/50,(int) 10.9);*/
    }
}
