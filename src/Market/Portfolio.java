package Market;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Date;

public class Portfolio {

    //portfolio balance data will be stored as date
    double balance;
    String username;
    EmailHandler emailHandler = new EmailHandler();
    LinkedList<Order> orders = new LinkedList<Order>();

    //only called when user first starts portfolio
    public Portfolio(double balance, String username){
        this.balance = balance;
        this.username = username;
    }

    //only called when a user already has an account
    public Portfolio(String username, double balance){
        this.balance = balance;
        this.username = username;
        //get existing positions from file
        try (
                FileReader fr = new FileReader("src/data/orders.txt");
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line = br.readLine();
            while(line != null){
                System.out.println(line);
                //if its long or short
                if(line.split(",")[6].equals("long") && username.equals(line.split(",")[7])) {
                    orders.add(new Order(line.split(",")[0], Double.parseDouble(line.split(",")[1]), Long.parseLong(line.split(",")[2]), Double.parseDouble(line.split(",")[3]), new double[]{Double.parseDouble(line.split(",")[4]), Double.parseDouble(line.split(",")[5])}, username));
                }else if(line.split(",")[6].equals("short") && username.equals(line.split(",")[7])){
                    orders.add(new ShortOrder(line.split(",")[0], Double.parseDouble(line.split(",")[1]), Long.parseLong(line.split(",")[2]), Double.parseDouble(line.split(",")[3]), new double[]{Double.parseDouble(line.split(",")[4]), Double.parseDouble(line.split(",")[5])}, username));
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePrice(int i){
        if(orders.get(i).updatePrice()){
            emailHandler.send("position closed " + orders.get(i).getStock().getTicker() + ", £" + orders.get(i).getCurrentSellPrice()+", pnl: "+orders.get(i).getPnLPercent());
            closeOrder(i);
        }
    }

    public void buyOrder(String ticker, double positionSize, double[] sltp){
        Order order = new Order(ticker.toLowerCase(), positionSize, sltp, username);
        orders.add(order);
        //buy at ask sell at bid
        balance -= positionSize;
        emailHandler.send("New long position added: " + order.getStock().getTicker() + ", £" + order.getBuyPrice());
    }

    public void shortOrder(String ticker, double positionSize, double[] sltp){
        ShortOrder shortOrder = new ShortOrder(ticker.toLowerCase(), positionSize, sltp, username);
        orders.add(shortOrder);
        balance -= positionSize;
        emailHandler.send("New short position added: " + shortOrder.getStock().getTicker() + ", £" + shortOrder.getBuyPrice());
    }


    public void closeOrder(int index){
        emailHandler.send("position closed " + orders.get(index).getStock().getTicker() + ", £" + orders.get(index).getCurrentSellPrice()+", pnl: "+orders.get(index).getPnLPercent());
        balance += orders.get(index).closeOrder(index);
        orders.remove(index);

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



    public double getBalance(){
        return balance;
    }

    public String getUsername(){
        return username;
    }

}
