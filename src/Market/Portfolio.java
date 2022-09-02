package Market;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Date;

public class Portfolio {

    //portfolio balance data will be stored as date
    double balance;
    LinkedList<Order> orders = new LinkedList<Order>();

    public Portfolio(double balance){
        this.balance = balance;
    }

    public void buyOrder(String ticker, double positionSize, double[] sltp){
        Order order = new Order(ticker, positionSize, sltp);
        orders.add(order);
        //buy at ask sell at bid
        balance -= positionSize;
    }

    //todo: stretch goal: make all these stock variables private and use getters and setters
    //todo: close positions
    //todo: cant enter positions if not enough balance
    public void diplayOrders(){
        int count = 1;
        for (Order order : orders) {
            System.out.println(count + ")");
            count++;
            System.out.println(order.stock.name);
            System.out.println(new Date((long)order.buyTime*1000));

            order.updatePrice();
            Double pnl = order.getPnL();
            DecimalFormat df = new DecimalFormat("#.##");
            //this takes into account both negative and positive values
            df.setRoundingMode(RoundingMode.DOWN);
            System.out.println(df.format(pnl));
            System.out.println("");
        }
    }

    public void closeBuyOrder(int index){
        orders.get(index-1).updatePrice();
        balance += orders.get(index-1).currentPrice[0];
        orders.remove(index-1);
    }

    public double getBalance(){
        return balance;
    }

}