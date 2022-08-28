import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Date;

public class Portfolio {

    //portfolio balance data will be stored as date
    double balance;
    LinkedList<Order> orders = new LinkedList<Order>();

    public Portfolio(double ballance){
        this.balance = balance;
    }

    public void buyOrder(String ticker, double[] sltp){
        Order order = new Order(ticker, sltp);
        orders.add(order);
    }

    //todo: stretch goal: make all these stock variables private and use getters and setters
    //todo: close positions
    public void diplayOrders(){
        for (Order order : orders) {
            System.out.println(order.stock.name);
            System.out.println(new Date((long)order.buyTime*1000));

            order.updatePrice();
            Double pnl = order.getPnL();
            DecimalFormat df = new DecimalFormat("#.##");
            //this takes into account both negative and positive values
            df.setRoundingMode(RoundingMode.DOWN);
            System.out.println(df.format(pnl));


        }


    }
}
