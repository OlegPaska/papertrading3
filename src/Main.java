import Market.JSONparser;
import Market.Portfolio;
import GUI.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Login.LoginHandler;
public class Main {


    public static void main(String[] args) throws InterruptedException {

        //LoginHandler login = new LoginHandler();


//        Portfolio portfolio = new Portfolio(30000, "john");
//        portfolio.buyOrder("AAPL", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("Z", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("TSLA", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("goog",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("tdoc", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("cvna",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("AAPL", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("Z", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("TSLA", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("goog",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("tdoc", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("cvna",5000,  new double[]{-1,-1});
//        GUIhandler gui = new GUIhandler();
//        gui.mainScreen(portfolio);

        GUIhandler guihandler = new GUIhandler();
        guihandler.login();
        //Basic basic = new Basic("aapl");

//        Runnable tick = new Runnable() {
//
//        };
//
//
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        executor.scheduleAtFixedRate(tick, 0, 60, TimeUnit.SECONDS);


        //

    }


}
