package GUI;

import Market.Order;
import Market.Portfolio;
import Market.Stock;
import com.sun.tools.javac.Main;

import javax.swing.*;


public class GUIhandler{

    public void login(){
        System.out.println("SEQUENCE: Login");
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Login myGUI = new Login(frame, this);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }

    public void mainScreen(Portfolio portfolio){
        System.out.println("SEQUENCE: Main Screen");
        JFrame frame = new JFrame("Portfolio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainScreen myGUI = new MainScreen(frame, portfolio);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }

    public void OpenPosition(Portfolio portfolio, boolean goingLong, Stock stock, JFrame mainscreenFrame){
        System.out.println("SEQUENCE: OpenPosition");
        JFrame frame = new JFrame("OpenPosition");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        OpenPosition myGUI = new OpenPosition(frame, this, goingLong, stock, portfolio, mainscreenFrame);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }

    public void PositionSettings(Portfolio portfolio, Order order, JFrame mainscreenFrame, int index){
        System.out.println("SEQUENCE: PositionSettings");
        JFrame frame = new JFrame("PositionSettings");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PositionSettings myGUI = new PositionSettings(frame, this, portfolio, order, mainscreenFrame, index);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }

}
