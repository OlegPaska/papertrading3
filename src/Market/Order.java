package Market;

import java.io.*;
import java.util.ArrayList;

public class Order {

    String username;
    Stock stock;
    double buyPrice;
    double buyEquity;
    double sharesBought;
    double currentPrice;
    long buyTime;
    //stop loss/take profit
    long currentTime;
    double[] sltp = new double[1];
    String ticker;

    String islong = "long";


    //TODO: make robust stock validation
    public Order(String ticker,double equity,  double[] sltp, String username){
        //setting variables
        this.username = username;
        stock = new Stock(ticker);
        buyPrice = stock.getPrice()[1];
        buyEquity = equity;
        sharesBought = equity/buyPrice;
        buyTime = System.currentTimeMillis() / 1000L;;
        this.ticker = ticker;
        this.sltp = sltp;
        //calling method to save order
        saveOrder();

    }

    //this is the one called by the portfolio class on startup  when reading from file
    public Order(String ticker, double buyPrice, long buyTime, double sharesBought,double[] sltp, String username){
        //setting variables
        this.username = username;
        stock = new Stock(ticker);
        this.buyPrice = buyPrice;
        this.sharesBought = sharesBought;
        this.buyEquity = sharesBought*buyPrice;
        this.buyTime = buyTime;
        this.ticker = ticker;
        this.sltp = sltp;

    }

    public void saveOrder(){
        //saved in a csv
        //stock ticker, buy price (ask), buy time, position size, stop loss, take profit, long order, username
        try (
                FileWriter fw = new FileWriter("src/data/orders.txt", true);
                PrintWriter pw = new PrintWriter(fw)
        ) {
            pw.println(ticker.toUpperCase()+","+buyPrice+","+buyTime+","+ sharesBought +","+sltp[0]+","+sltp[1]+",long,"+username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double closeOrder(int index){
        //closes order and returns sell price
        updatePrice();
        ArrayList<Integer> tickerIndex = new ArrayList<Integer>();
        ArrayList<String> lines = new ArrayList<String>();
        int count = 0;
        //finds stock in file and saves all stocks after in arraylist
        try (
                FileReader fr = new FileReader("src/data/orders.txt");
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line = br.readLine();
            //finds the ticker and saves file contents into lines

            while (line != null) {
                lines.add(line);
                //finds index of all orders with specified ticker belonging to the user
                if(line.split(",")[7].equals(username)) {
                    tickerIndex.add(count);
                }
                count++;
                line = br.readLine();
            }


            //removes all orders with the ticker
            count = 0;
            for(int i: tickerIndex){
                if(count == index) {
                    lines.remove(i);
                }
                count++;
            }

            //reconstructs orders file
            try (
                    FileWriter fw = new FileWriter("src/data/orders.txt", false);
                    PrintWriter pw = new PrintWriter(fw)
            ) {
                for(int i = 0;i<lines.size(); i++){
                    pw.println(lines.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error accessing saved data [closeOrder()]");
        }

        return getCurrentSellPrice();

    }
    public boolean updatePrice(){
        //if this returns true then the position needs to be closed
        //gets the bid (this is what the sellers will buy the stock for)
        currentPrice = stock.getPrice()[0];
        currentTime = System.currentTimeMillis() / 1000L;
        return checkSLTP();

    }

    public String isLong(){
        return "long";
    }

    public boolean checkSLTP(){
        //triggers stop loss take profit and closes order
        //if true closes order
        if (currentPrice <= sltp[0] && sltp[0] != -1){
            return true;
        } else if(currentPrice >= sltp[1] && sltp[1] != -1){
            return true;
        }else{
            return false;
        }
    }

    public long getBuyTime(){
        return buyTime;
    }

    public String getUsername() {
        return username;
    }

    public double getCurrentPrice(){
        return currentPrice;
    }

    public Stock getStock(){
        return stock;
    }
    public double getBuyPrice(){
        return buyPrice;
    }

    public double getBuyEquity(){
        return buyEquity;
    }
    public double getSharesBought(){return sharesBought;}

    public double[] getSltp() {
        return sltp;
    }

    public double getCurrentSellPrice(){
        return currentPrice* sharesBought;
    }

    public double getPnL(){
        return getCurrentSellPrice()-buyEquity;
    }

    public double getPnLPercent(){
        return (getPnL()/buyEquity)*100;
    }




}
