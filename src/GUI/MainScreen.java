package GUI;

import Market.APIhandler;
import Market.Order;
import Market.Portfolio;
import Market.Stock;

import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;

public class MainScreen extends JPanel {
    Portfolio portfolio;
    Stock stock;
    public MainScreen(Portfolio portfolio){
        this.portfolio = portfolio;
        final int[] scrollSetting = {0};
        LinkedList<Order> orders = portfolio.getOrders();

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






        JLabel stockTickerLabel = new JLabel("<stock ticker>");
        stockTickerLabel.setBounds(300, 30, 250, 40);
        stockTickerLabel.setFont(new Font(stockTickerLabel.getName(), Font.PLAIN, determineFontSize(stockTickerLabel)));
        add(stockTickerLabel);

        JLabel fullNameLabel = new JLabel("<fullNameLabel>");
        fullNameLabel.setBounds(300, 110, 250, 20);
        fullNameLabel.setFont(new Font(fullNameLabel.getName(), Font.PLAIN, determineFontSize(fullNameLabel)));
        add(fullNameLabel);

        JLabel fdaLabel = new JLabel("<fdaLabel>");
        fdaLabel.setBounds(300, 150, 250, 20);
        fdaLabel.setFont(new Font(fdaLabel.getName(), Font.PLAIN, determineFontSize(fdaLabel)));
        add(fdaLabel);

        JLabel averageVolumeLabel = new JLabel("<averageVolumeLabel>");
        averageVolumeLabel.setBounds(300, 190, 250, 30);
        averageVolumeLabel.setFont(new Font(averageVolumeLabel.getName(), Font.PLAIN, determineFontSize(averageVolumeLabel)));
        add(averageVolumeLabel);

        JButton  buyButton = new JButton("BUY"); // search icon
        buyButton.setForeground(Color.BLACK);
        buyButton.setBackground(Color.GREEN);
        buyButton.setOpaque(true);
        buyButton.setBounds(580,50, 150, 75);
        buyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    GUIhandler guIhandler = new GUIhandler();
                    guIhandler.OpenPosition(portfolio, true, stock);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(buyButton);

        JButton  shortButton = new JButton("SHORT"); // search icon
        shortButton.setForeground(Color.BLACK);
        shortButton.setBackground(Color.RED);
        //shortButton.setFont(new Font());
        shortButton.setOpaque(true);
        shortButton.setBounds(750,50, 150, 75);
        shortButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    GUIhandler guIhandler = new GUIhandler();
                    guIhandler.OpenPosition(portfolio, false, stock);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(shortButton);

        JButton  drawGraphButton = new JButton("Draw Graph"); // search icon
        drawGraphButton.setBackground(Color.red);
        drawGraphButton.setContentAreaFilled(false);
        drawGraphButton.setBounds(625,150, 200, 75);
        add(drawGraphButton);

        JTextField searchBar = new JTextField();
        searchBar.setMargin(new Insets(2,13,3,2)); // padding the text
        searchBar.setBounds(25,110, 170, 30);
        add(searchBar);


        JButton  searchButton = new JButton(""); // search icon
        searchButton.setIcon(new ImageIcon(new ImageIcon("src/data/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        searchButton.setContentAreaFilled(false);
        searchButton.setBounds(195,110, 30, 30);
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    HashMap<String, String> dashboardData = getDashboardData(searchBar.getText());
                    stockTickerLabel.setText(dashboardData.get("ticker").toUpperCase());
                    fullNameLabel.setText(dashboardData.get("name"));
                    fdaLabel.setText("50 Day Average: "+dashboardData.get("fda"));
                    averageVolumeLabel.setText("Average Volume: "+dashboardData.get("volume"));
                    buyButton.setText("<html><h2>BUY</h2><h2>"+dashboardData.get("ask")+"</h2>");
                    shortButton.setText("<html><h2>BUY</h2><h2>"+dashboardData.get("bid")+"</h2>");


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(searchButton);



        JButton[] positionLabel = new JButton[orders.size()];
        for(int i = 0; i<orders.size();i++){
            if(i<4) {
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

        JButton  upButton = new JButton(""); // search icon
        upButton.setIcon(new ImageIcon(new ImageIcon("src/data/arrowup.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); // scaling the image properly so that there is no stretch
        upButton.setContentAreaFilled(false);
        upButton.setBounds(230,510, 30, 30);
        upButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if(scrollSetting[0]-1 >= 0) {
                        scrollSetting[0]--;
                        System.out.println("going up");

                        for (int i = 0; i < 3 + orders.size(); i++) {
                            if (i < 4) {
                                DecimalFormat df = new DecimalFormat("#.##");
                                String ticker = orders.get((scrollSetting[0]*4)+i).getStock().getTicker().replaceAll("\"", "").toUpperCase();
                                String name = orders.get((scrollSetting[0]*4)+i).getStock().getName().replaceAll("\"", "");
                                double buyPrice = orders.get((scrollSetting[0]*4)+i).getBuyInPrice();
                                double sellPrice = orders.get((scrollSetting[0]*4)+i).getCurrentSellPrice();
                                double pnl = (sellPrice / buyPrice) * 100 - 100;
                                positionLabel[i].setText("<html><p>" + ticker + "</p><p>" + name + "</p><p>£" + df.format(buyPrice) + "  £" + df.format(sellPrice) + "  " + df.format(pnl) + "%</p></html>");



                                System.out.println("done");
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(upButton);

        JButton  downButton = new JButton(""); // search icon
        downButton.setIcon(new ImageIcon(new ImageIcon("src/data/arrowdown.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH))); // scaling the image properly so that there is no stretch
        downButton.setContentAreaFilled(false);
        downButton.setBounds(230,545, 30, 30);
        downButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if((scrollSetting[0] +1)*4 < orders.size()) {
                        scrollSetting[0]++;
                        System.out.println("going down");
                        for (int i = 0; i < 3 + orders.size(); i++) {
                            if (i < 4) {
                                DecimalFormat df = new DecimalFormat("#.##");
                                String ticker = orders.get((scrollSetting[0]*4)+i).getStock().getTicker().replaceAll("\"", "").toUpperCase();
                                String name = orders.get((scrollSetting[0]*4)+i).getStock().getName().replaceAll("\"", "");
                                double buyPrice = orders.get((scrollSetting[0]*4)+i).getBuyInPrice();
                                double sellPrice = orders.get((scrollSetting[0]*4)+i).getCurrentSellPrice();
                                double pnl = (sellPrice / buyPrice) * 100 - 100;

                                positionLabel[i].setText("<html><p>" + ticker + "</p><p>" + name + "</p><p>£" + df.format(buyPrice) + "  £" + df.format(sellPrice) + "  " + df.format(pnl) + "%</p></html>");


                                System.out.println("done");
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(downButton);

    }

    public HashMap getDashboardData(String ticker){
        this.stock = new Stock(ticker);
        HashMap<String, String> dashboardData = new HashMap<String, String>();
        dashboardData.put("ticker", ticker);
        dashboardData.put("bid", Double.toString(stock.getPrice()[0]));
        dashboardData.put("ask", Double.toString(stock.getPrice()[1]));
        dashboardData.put("name", stock.getName());

        //simplifying
        int volume = Integer.parseInt(stock.getVolume());
        if(volume>1000000000){
            dashboardData.put("volume", Integer.toString(volume/1000000000)+"b");
        } else if (volume>1000000) {
            dashboardData.put("volume", Integer.toString(volume/1000000)+"m");
        } else{
            dashboardData.put("volume", Integer.toString(volume));
        }
        dashboardData.put("fda", stock.getFda());
        return dashboardData;
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
