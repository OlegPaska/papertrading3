package GUI;

import Market.Portfolio;

import javax.swing.*;
import java.awt.*;

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


        //todo: this
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
}
