package GUI;

import javax.swing.*;
import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import Market.JSONparser;
import Market.Stock;
import jdk.swing.interop.SwingInterOpUtils;

public class Basic extends JPanel {
    //this is also just for reference
    private JFrame frame;
    String ticker;

    double screenx;
    double screeny;
    int marginx;
    int marginy;
    double min;
    double max;
    double[][] data;
    int numOfCandles = 50;

    public Basic(String ticker) {
        // initialise the window
        this.ticker = ticker;
        frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        setLayout(null);

        screenx = 700;
        screeny = 450;
        marginx = 100;
        marginy = 50;
        screenx-=marginx;
        screeny-=marginy;
        double[] minmax = getMinMax();
        min=minmax[0];
        max=minmax[1];
        data=getData();


        // create the canvas that will hold the actual graphics

        frame.getContentPane().add(this);
        labelAxis((int)screenx, (int)screeny, (int)marginx, (int)marginy, min, max);

        // display the frame AFTER adding the panel to prevent drawing glitches
        frame.setVisible(true);

        System.out.println("done");

    }

    public double[][] getData() {
        JSONparser jason=new JSONparser();
        return jason.getGraphData(ticker);
    }

    public double[] getMinMax(){
        JSONparser jason = new JSONparser();
        //{[open],[close],[high],[low]}
        double[][] data = jason.getGraphData(ticker);
        //getting min and max to scale graph to screen width
        double min = data[3][0];
        double max = data[2][0];
        for(int i = data[2].length-1; i>=data[2].length-numOfCandles-1;i--){
            if(data[3][i] < min){
                min = data[3][i];
            }
            if(data[2][i] > max){
                max = data[2][i];
            }
        }
        return new double[]{min,max};
    }

    @Override
    public void paintComponent(Graphics g) {

        int count=0;
        for (int i = data[0].length-1; i>=data[0].length-numOfCandles-1;i--){

            //todo: there is a problem with the wicks
            //green ones have no upper wick and red ones have no lower wick
            if (data[0][i] < data[1][i]){
                g.setColor(Color.green);
                //position from 0 to 1 signifying the closing/opening position relative to local minimums and maximums on a screen axis
                double closeMagnitude = 1-((data[1][i]-min)/(max-min));
                double openMagnitude = 1-((data[0][i]-min)/(max-min));
                double lowMagnitude = 1-((data[3][i]-min)/(max-min));
                double highMagnitude = 1-((data[2][i]-min)/(max-min));
                g.fillRect((int)(screenx-((screenx/numOfCandles)*count))+marginx, (int)((closeMagnitude)*screeny)-marginy, (int)(screenx/numOfCandles), (int)(((openMagnitude-closeMagnitude))*screeny));
                g.fillRect((int)(screenx-((screenx/numOfCandles)*count)+screenx/(numOfCandles*2))+marginx, (int)(openMagnitude*screeny)-marginy, 2, (int)((lowMagnitude-openMagnitude)*screeny));
                g.fillRect((int)(screenx-((screenx/numOfCandles)*count)+screenx/(numOfCandles*2))+marginx, (int)(closeMagnitude*screeny)-marginy, 2, (int)((highMagnitude-closeMagnitude)*screeny));


            } else{
                g.setColor(Color.red);
                double closeMagnitude = 1-((data[1][i]-min)/(max-min));
                double openMagnitude = 1-((data[0][i]-min)/(max-min));
                double lowMagnitude = 1-((data[3][i]-min)/(max-min));
                double highMagnitude = 1-((data[2][i]-min)/(max-min));
                g.fillRect((int)(screenx-((screenx/numOfCandles)*count))+marginx, (int)((openMagnitude)*screeny)-marginy, (int)(screenx/numOfCandles), (int)(((closeMagnitude-openMagnitude))*screeny));
                g.fillRect((int)(screenx-((screenx/numOfCandles)*count)+screenx/(numOfCandles*2))+marginx, (int)(lowMagnitude*screeny)-marginy, 2, (int)((openMagnitude-lowMagnitude)*screeny));
                g.fillRect((int)(screenx-((screenx/numOfCandles)*count)+screenx/(numOfCandles*2))+marginx, (int)(highMagnitude*screeny)-marginy, 2, (int)((closeMagnitude-highMagnitude)*screeny));

            }
            count++;

        }
        System.out.println("finished making candlestick graph");
        g.setColor(Color.BLACK);
        //draws axis lines
        g.fillRect(marginx, 0, 2, (int)screeny);
        g.fillRect(marginx, (int)screeny, (int)screenx, 2);



/*
        int left = 50;    // hard-coded just for testing
        int top = 80;
        int width = 40;
        int height = 150;
        g.setColor(Color.red);
        g.fillRect(left, top, width, height);
        g.fillRect((int)10.5,(int)10.3,(int) screenx/50,(int) 10.9);*/
    }
    public void labelAxis(int screenx, int screeny, int marginx, int marginy, double min, double max){
        //make this output price markers at regular intervals on the y axis
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        JLabel maxLabel = new JLabel(df.format(max));
        maxLabel.setBounds(0,0,100, 10);
        maxLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(maxLabel);

        JLabel minLabel = new JLabel(df.format(min));
        minLabel.setBounds(0,screeny,100, 10);
        minLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(minLabel);

        Date earliestDate = new java.util.Date((long)data[4][data[0].length-numOfCandles-1]*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd HH:mm");
        Date latestDate = new java.util.Date((long)data[4][data[0].length-1]*1000L);

        JLabel earliestTimeLabel = new JLabel(sdf.format(earliestDate));
        earliestTimeLabel.setBounds(100,screeny,100, marginy);
        earliestTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(earliestTimeLabel);

        JLabel latestTimeLabel = new JLabel(sdf.format(latestDate));
        latestTimeLabel.setBounds(screenx+marginy-100,screeny,100, marginy);
        latestTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(latestTimeLabel);

    }
}
