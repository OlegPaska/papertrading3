package Market;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        //get existing positions from file
        try (
                FileReader fr = new FileReader("src/data/orders.txt");
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line = br.readLine();
            while(line != null){
                System.out.println(line);
                //if its long or short
                if(line.split(",")[6].equals("long")) {
                    orders.add(new Order(line.split(",")[0], Double.parseDouble(line.split(",")[1]), Long.parseLong(line.split(",")[2]), Double.parseDouble(line.split(",")[3]), new double[]{Double.parseDouble(line.split(",")[4]), Double.parseDouble(line.split(",")[5])}));
                }
                    line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
    public void displayOrders(){
        int count = 1;
        for (Order order : orders) {
            System.out.println(count + ")");
            count++;
            order.updatePrice();
            Double pnl = order.getPnL();
            DecimalFormat df = new DecimalFormat("#.##");
            //this takes into account both negative and positive values
            df.setRoundingMode(RoundingMode.DOWN);

            System.out.println("stock name: " + order.stock.name);
            System.out.println("buy time: " + new Date((long)order.getBuyTime()*1000));
            System.out.println("current pnl: " + df.format(pnl));
            System.out.println("");
        }
    }

    public LinkedList<Order> getOrders(){
        return orders;
    }

    public void closeOrder(int index){
        balance += orders.get(index-1).closeOrder();
        orders.remove(index-1);
    }

    public double getBalance(){
        return balance;
    }

}
