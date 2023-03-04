package GUI;

import Market.Order;
import Market.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class PositionSettings extends JPanel {
    JFrame frame;
    GUIhandler guIhandler;
    Portfolio portfolio;
    Order order;
    JFrame mainscreenFrame;
    JLabel orderSummaryLabel;
    JButton confirmButton;
    public PositionSettings(JFrame frame, GUIhandler guIhandler, Portfolio portfolio, Order order, JFrame mainscreenFrame, int index){
        this.frame = frame;
        this.guIhandler = guIhandler;
        this.portfolio = portfolio;
        this.order = order;
        this.mainscreenFrame = mainscreenFrame;

        this.setPreferredSize(new Dimension(230, 230));
        setLayout(null);

        order.updatePrice();
        DecimalFormat df = new DecimalFormat("#.##");
        String ticker = order.getStock().getTicker().replaceAll("\"", "").toUpperCase();
        String name = order.getStock().getName().replaceAll("\"", "");
        double buyEquity = order.getBuyEquity();
        double pnl = order.getPnL();
        double pnLPercent = order.getPnLPercent();
        //todo make these go red and green

        String pnlFont = "<font color='green'>";
        String pnlPercentFont = "<font color='green'>";
        if(pnl < 0){
            pnlFont = "<font color='red'>";
        }
        if(pnLPercent < 0){
            pnlPercentFont = "<font color='red'>";
        }

        orderSummaryLabel = new JLabel("<html><p>" + ticker + "</p><p>" + name + "</p><p>Initial position size: £" + df.format(buyEquity) + "</p><p>PnL(£): "+pnlFont+"£" + df.format(pnl) + "</p><p>PnL(%)  " + pnlPercentFont + df.format(pnLPercent) + "%</p></html>");
        orderSummaryLabel.setBounds(10,5,200, 100);
        add(orderSummaryLabel);

        confirmButton = new JButton("<html><p>Close "+order.isLong()+"</p><p>"+df.format(order.getCurrentSellPrice())+"</p></html>");
        confirmButton.setBackground(Color.red);
        confirmButton.setFont(new Font(Font.SERIF, Font.BOLD,  14));
        confirmButton.setBounds(10, 120, 100, 50);
        confirmButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    portfolio.closeOrder(index);
                    mainscreenFrame.dispose();
                    guIhandler.mainScreen(portfolio);
                    frame.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(confirmButton);
    }
}
