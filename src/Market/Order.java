package Market;

import java.io.*;

public class Order {

    Stock stock;

    private double buyPrice;
    private double buyPositionSize;
    private double currentPrice;
    private long buyTime;
    //stop loss/take profit
    private long currentTime;
    private double[] sltp = new double[1];
    String ticker;


    //TODO: make robust stock validation
    public Order(String ticker,double positionSize,  double[] sltp){
        //setting variables
        stock = new Stock(ticker);
        buyPrice = stock.getPrice()[1];
        buyPositionSize = positionSize;
        buyTime = System.currentTimeMillis() / 1000L;;
        this.ticker = ticker;
        this.sltp = sltp;
        //calling method to save order

    }

    public void saveOrder(boolean normalOrder){
        //saved in a csv
        //stock ticker, buy price (ask), buy time, stop loss, take profit
        try (
                FileWriter fw = new FileWriter("src/data/orders.txt", true);
                PrintWriter pw = new PrintWriter(fw)
        ) {
            pw.println(""+ticker+buyPrice+buyTime+sltp[0]+sltp[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeOrder(){
        //closes order and returns sell price
        updatePrice();
        int count = 0;
        //finds stock in file
        try (
                FileReader fr = new FileReader("src/data/api_cache.txt");
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line = br.readLine();
            //finds the ticker
            while (line != null && line.split(",")[0].equals(ticker)) {
                count++;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error accessing saved data [closeOrder()]");
        }

        //deletes entry
        //todo: none of this works i need to save the previous records into queue or stack and then add the rest without deleted thing
        try (
                FileWriter fw = new FileWriter("src/data/orders.txt", false);
                PrintWriter pw = new PrintWriter(fw)
        ) {
            for(int i = 0; i <= count; i++){
                //how do i iterate through this
            }
            pw.println("[POSITION_CLOSED]");
        } catch (IOException e) {
            e.printStackTrace();
        }


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

    public double getCurrentPrice(){
        return currentPrice;
    }

    public Stock getStock(){
        return stock;
    }

    public double getBuyInPrice(){
        return buyPositionSize;
    }

    public double getCurrentSellPrice(){
        double fractionalSharesBought = buyPositionSize/buyPrice;
        return currentPrice*fractionalSharesBought;
    }

    public double getPnL(){
        return buyPositionSize-getCurrentSellPrice();
    }




}
