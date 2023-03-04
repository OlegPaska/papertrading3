package GUI;

import Market.Portfolio;
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
    JLabel quantityLabel;
    JLabel stopLossLabel;
    JLabel takeProfitLabel;
    JTextField stopLossInput;
    JTextField takeProfitInput;


    JTextField quantityInput;
    Stock stock;
    public OpenPosition(JFrame frame, GUIhandler guiHandler, boolean goingLong, Stock stock, Portfolio portfolio, JFrame mainscreenFrame){
        this.stock = stock;
        this.frame = frame;
        this.guiHandler = guiHandler;

        System.out.println("GUI SEQUENCE: OpenPosition");
        this.setPreferredSize(new Dimension(250, 300));
        setLayout(null);

        stockInfo = new JLabel();
        stockInfo.setBounds(10, 0, 200, 100);
        if (goingLong == true) {
            stockInfo.setText("<html><p>" + stock.getTicker().toUpperCase() + "</p><p>" + stock.getName() + "</p><p>" + stock.getPrice()[1]+"</p></html>");
        }else{
            stockInfo.setText("<html><p>" + stock.getTicker().toUpperCase() + "</p><p>" + stock.getName() + "</p><p>" + stock.getPrice()[0]+"</p></html>");
        }
        add(stockInfo);

        quantityInput = new JTextField();
        quantityInput.setBounds(10,110,165, 25);
        add(quantityInput);

        quantityLabel = new JLabel("Quantity (£)");
        quantityLabel.setBounds(10,80,165, 25);
        add(quantityLabel);

        stopLossInput = new JTextField();
        stopLossInput.setBounds(10,170,165, 25);
        add(stopLossInput);

        stopLossLabel = new JLabel("Stop Loss");
        stopLossLabel.setBounds(10,140,165, 25);
        add(stopLossLabel);

        takeProfitInput = new JTextField();
        takeProfitInput.setBounds(10,230,165, 25);
        add(takeProfitInput);

        takeProfitLabel = new JLabel("Take Profit");
        takeProfitLabel.setBounds(10,200,165, 25);
        add(takeProfitLabel);

        orderButton = new JButton();
        orderButton.setBounds(10, 270, 200, 20);
        orderButton.setFont(new Font(Font.SERIF, Font.BOLD,  14));
        if(goingLong == true){
            orderButton.setText("Place Long Order");
        } else{
            orderButton.setText("Place Short Order");
        }
        orderButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    //please dont look at this i know theres a better way. im tired
                    double[] sltp = {,};
                    if(stopLossInput.getText().equals("") || takeProfitInput.getText().equals("")) {
                        if (stopLossInput.getText().equals("") && !(takeProfitInput.getText().equals(""))) {
                            sltp[0] = -1;
                            sltp[1] = Double.parseDouble(takeProfitInput.getText());
                        }
                        if (takeProfitInput.getText().equals("") && !stopLossInput.getText().equals("")) {
                            sltp[1] = -1;
                            sltp[0] = Double.parseDouble(stopLossInput.getText());
                        } else{
                            sltp = new double[]{-1,-1};
                        }
                    }else{
                        sltp = new double[]{Double.parseDouble(stopLossInput.getText()), Double.parseDouble(takeProfitInput.getText())};
                    }

                    if(goingLong) {
                        portfolio.buyOrder(stock.getTicker(), Double.parseDouble(quantityInput.getText()), sltp);
                    }else{
                        portfolio.shortOrder(stock.getTicker(), Double.parseDouble(quantityInput.getText()), sltp);
                    }

                    mainscreenFrame.dispose();
                    GUIhandler guIhandler = new GUIhandler();
                    guIhandler.mainScreen(portfolio);
                    frame.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(orderButton);



    }

}
