package Market;

import java.io.*;
import java.util.ArrayList;

public class Order {

    String username;
    Stock stock;
    double buyPrice;
    double buyPositionSize;
    double currentPrice;
    long buyTime;
    //stop loss/take profit
    long currentTime;
    double[] sltp = new double[1];
    String ticker;

    String orderType = "long";


    //TODO: make robust stock validation
    public Order(String ticker,double positionSize,  double[] sltp, String username){
        //setting variables
        this.username = username;
        stock = new Stock(ticker);
        buyPrice = stock.getPrice()[1];
        buyPositionSize = positionSize;
        buyTime = System.currentTimeMillis() / 1000L;;
        this.ticker = ticker;
        this.sltp = sltp;
        //calling method to save order
        saveOrder();

    }

    //this is the one called by the portfolio class on startup  when reading from file
    public Order(String ticker, double buyPrice, long buyTime, double buyPositionSize,double[] sltp, String username){
        //setting variables
        this.username = username;
        stock = new Stock(ticker);
        this.buyPrice = buyPrice;
        buyPositionSize = buyPositionSize;
        this.buyTime = buyTime;
        this.ticker = ticker;
        this.sltp = sltp;

    }

    public void saveOrder(){
        //saved in a csv
        //stock ticker, buy price (ask), buy time, position size, stop loss, take profit, long order?
        try (
                FileWriter fw = new FileWriter("src/data/orders.txt", true);
                PrintWriter pw = new PrintWriter(fw)
        ) {
            pw.println(ticker+","+buyPrice+","+buyTime+","+buyPositionSize+","+sltp[0]+","+sltp[1]+",long,"+username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double closeOrder(){
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
                //finds index of all orders with specified ticker
                if(line.split(",")[0].equals(ticker)) {
                    tickerIndex.add(count);
                }
                count++;
                line = br.readLine();
            }


            //removes all orders with the ticker
            for(int i: tickerIndex){
                //this is why i couldnt use queue or stack
                lines.remove(i);
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

        return getPnL();

    }
    public boolean updatePrice(){
        //if this returns true then the position needs to be closed
        //gets the bid (this is what the sellers will buy the stock for)
        currentPrice = stock.getPrice()[0];
        currentTime = System.currentTimeMillis() / 1000L;
        return checkSLTP();

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

    public double getBuyInPrice(){
        return buyPositionSize * buyPrice;
    }
    public double getBuyPositionSize(){return buyPositionSize;}

    public double[] getSltp() {
        return sltp;
    }

    public double getCurrentSellPrice(){
        double fractionalSharesBought = buyPositionSize/buyPrice;
        return currentPrice*fractionalSharesBought;
    }

    public double getPnL(){
        return getBuyInPrice()-getCurrentSellPrice();
    }

    public double getPnLPercent(){
        return getPnL()/getBuyInPrice();
    }




}
