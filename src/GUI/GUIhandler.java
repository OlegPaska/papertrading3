package GUI;

import Market.Portfolio;
import com.sun.tools.javac.Main;

import javax.swing.*;


public class GUIhandler{

    public void login(){
        System.out.println("SEQUENCE: Login");
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Login myGUI = new Login(frame, this);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }

    public void mainScreen(Portfolio portfolio){
        System.out.println("SEQUENCE: Main Screen");
        JFrame frame = new JFrame("Portfolio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainScreen myGUI = new MainScreen(portfolio);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }
}
