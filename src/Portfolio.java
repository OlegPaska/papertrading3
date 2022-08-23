import java.util.LinkedList;

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
}
