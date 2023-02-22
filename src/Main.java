import Market.JSONparser;
import Market.Portfolio;
import GUI.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {

    //todo: use less api calls

    public static void main(String[] args) throws InterruptedException {

//        Market.APIhandler API = new Market.APIhandler();
//        Market.JSONparser jason = new Market.JSONparser();
//        double[] bidask = jason.getBidAsk("HSBA.L");
//        System.out.println(bidask[0]);
//        System.out.println(bidask[1]);
//        Market.Stock stock = new Market.Stock("AAPL");

//        GUIhandler gui = new GUIhandler();
//        gui.login();

     //   GUI geui = new GUI();

//
     Portfolio portfolio = new Portfolio(25000);
         //this gives off some wacky results
//        //todo: fix it
//
//        portfolio.buyOrder("AAPL", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("Z", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("TSLA", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("goog",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("tdoc", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("cvna",5000,  new double[]{-1,-1});
        portfolio.clo
        portfolio.displayOrders();
        //GUIhandler gui = new GUIhandler();
        //gui.mainScreen(portfolio);
        //TimeUnit.SECONDS.sleep(60);
//        portfolio.diplayOrders();
//        portfolio.closeBuyOrder(1);
//
//        System.out.println(portfolio.getBalance());
//        JSONparser jason = new JSONparser();
//        System.out.println(Arrays.deepToString(jason.getGraphData("AAPL")));

        //System.out.println(API.getQuote(""));

        //

    }


}
