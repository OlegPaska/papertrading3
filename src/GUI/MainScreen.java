package GUI;

import Market.Order;
import Market.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class MainScreen extends JPanel {
    public MainScreen(Portfolio portfolio){
        System.out.println("GUI SEQUENCE: mainscreen construction");
        this.setPreferredSize(new Dimension(985,638));
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome " + "<username>");
        welcomeLabel.setBounds(15,15, 280, 25);
        welcomeLabel.setFont(new Font(welcomeLabel.getName(), Font.PLAIN, determineFontSize(welcomeLabel)));

        add(welcomeLabel);

        JLabel balanceLabel = new JLabel("£" + portfolio.getBalance());
        balanceLabel.setBounds(15, 50, 215, 20);
        balanceLabel.setFont(new Font(balanceLabel.getName(), Font.PLAIN, determineFontSize(balanceLabel)));
        add(balanceLabel);

        //todo: get daily pnl and change this from red to green
        JLabel pnlLabel = new JLabel("<pnl today>");
        pnlLabel.setBounds(15,75, 125, 15);
        pnlLabel.setFont(new Font(pnlLabel.getName(), Font.PLAIN, determineFontSize(pnlLabel)));
        add(pnlLabel);



        JTextField searchBar = new JTextField();
        searchBar.setMargin(new Insets(2,13,3,2)); // padding the text
        searchBar.setBounds(25,110, 170, 30);
        add(searchBar);


        
        JButton  button = new JButton(""); // search icon
        button.setIcon(new ImageIcon(new ImageIcon("src/data/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); // scaling the image properly so that there is no stretch
        button.setContentAreaFilled(false);
        button.setBounds(195,110, 30, 30);
        add(button);



        //this for loop will overflow the positionLabel array if there are too many orders
        //todo: fix it
        LinkedList<Order> orders = portfolio.getOrders();
        JButton[] positionLabel = new JButton[orders.size()];
        for(int i = 0; i<orders.size();i++){

            DecimalFormat df = new DecimalFormat("#.##");
            String ticker = orders.get(i).getStock().getTicker().replaceAll("\"", "").toUpperCase();
            String name = orders.get(i).getStock().getName().replaceAll("\"", "");
            double buyPrice = orders.get(i).getBuyInPrice();
            double sellPrice = orders.get(i).getCurrentSellPrice();
            double pnl = (sellPrice / buyPrice) * 100 - 100;
            //todo make these go red and green


            positionLabel[i] = new JButton("<html><p>" + ticker + "</p><p>" + name + "</p><p>£" + df.format(buyPrice) + "  £" + df.format(sellPrice) + "  " + df.format(pnl) + "%</p></html>");
            positionLabel[i].setFont(new Font(("positionLabel" + i), Font.PLAIN, 20));
            positionLabel[i].setBounds(25, 150 + (105 * i), 200, 100);
            positionLabel[i].setHorizontalAlignment(SwingConstants.LEFT);
            positionLabel[i].setContentAreaFilled(false);

            int lastI = i;
            positionLabel[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    try {
                        System.out.println("clicked on positionLabel " + lastI);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            add(positionLabel[i]);
        }



    }

    //nabbed off stackoverflow: https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size
    public int determineFontSize(JLabel label){
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();

        // Find out how much the font can grow in width.
        double widthRatio = (double)componentWidth / (double)stringWidth;

        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        return fontSizeToUse;
    }

    //make this
    public void refreshPage(){

    }


}
