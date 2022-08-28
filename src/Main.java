import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        APIhandler API = new APIhandler();
//        JSONparser jason = new JSONparser();
//        double[] bidask = jason.getBidAsk("HSBA.L");
//        System.out.println(bidask[0]);
//        System.out.println(bidask[1]);
//        Stock stock = new Stock("AAPL");

        Portfolio portfolio = new Portfolio(20000);

        portfolio.buyOrder("HSBA.L", new double[]{-1,-1});
        //TimeUnit.SECONDS.sleep(60);
        portfolio.diplayOrders();


        //System.out.println(API.getQuote(""));

    }
}
