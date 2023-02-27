package GUI;

import Market.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class OpenPosition extends JPanel{
    // canvas for other GUI widgets

    GUIhandler guiHandler;
    JFrame frame;

    JButton orderButton;
    JLabel stockInfo;
    JLabel positionLabel;
    JCheckBox shorting;
    JCheckBox longing;

    JLabel longLable;
    JLabel shortLable;
    JLabel priceLabel;
    JTextField price;
    JTextField stockSearchBar;
    Stock stock;
    public OpenPosition(JFrame frame, GUIhandler guiHandler, boolean goingLong, Stock stock){
        this.stock = stock;
        this.frame = frame;
        this.guiHandler = guiHandler;

        System.out.println("GUI SEQUENCE: OpenPosition");
        this.setPreferredSize(new Dimension(250, 180));
        setLayout(null);

        stockSearchBar = new JTextField();
        stockSearchBar.setText(stock.getTicker().toUpperCase());
        stockSearchBar.setBounds(10,30,165, 25);
        add(stockSearchBar);

        stockInfo = new JLabel();
        stockInfo.setBounds(10, 60, 200, 100);
        if (goingLong == true) {
            stockInfo.setText("<html><p>" + stock.getTicker().toUpperCase() + "</p><p>" + stock.getName() + "</p><p>" + stock.getPrice()[1]+"</p></html>");
        }else{
            stockInfo.setText("<html><p>" + stock.getTicker().toUpperCase() + "</p><p>" + stock.getName() + "</p><p>" + stock.getPrice()[0]+"</p></html>");
        }
        add(stockInfo);



        orderButton = new JButton();
        orderButton.setBounds(10, 140, 200, 20);
        orderButton.setFont(new Font(Font.SERIF, Font.BOLD,  14));
        if(goingLong == true){
            orderButton.setText("Place Long Order");
        } else{
            orderButton.setText("Place Short Order");
        }
        add(orderButton);



    }

}
