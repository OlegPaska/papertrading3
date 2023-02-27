package Market;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ShortOrder extends Order{

    //called when user places an order
    public ShortOrder(String ticker,double positionSize,  double[] sltp, String username){
        //setting variables
        super(ticker, positionSize, sltp, username);


    }

    //this is the one called by the portfolio class on startup when reading from file
    public ShortOrder(String ticker, double buyPrice, long buyTime, double buyPositionSize,double[] sltp, String username){
        //setting variables
        super(ticker,buyPrice, buyTime, buyPositionSize, sltp, username);

        //calling method to save order
    }

    @Override
    public void saveOrder() {
        //saved in a csv
        //stock ticker, buy price (ask), buy time, position size, stop loss, take profit, long order?
        try (
                FileWriter fw = new FileWriter("src/data/orders.txt", true);
                PrintWriter pw = new PrintWriter(fw)
        ) {
            pw.println(ticker+","+buyPrice+","+buyTime+","+buyPositionSize+","+sltp[0]+","+sltp[1]+",short,"+username);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //these two are just simply reversed
    @Override
    public double getPnL() {
        return super.getPnL()*-1;
    }

    @Override
    public double getPnLPercent() {
        return super.getPnLPercent() *-1;
    }
}
