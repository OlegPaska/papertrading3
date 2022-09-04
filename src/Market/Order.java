package Market;

public class Order {

    Stock stock;
    double[] buyPrice = new double[1];
    double buyPositionSize;
    double[] currentPrice = new double[1];
    long buyTime;
    //stop loss/take profit
    long currentTime;
    double[] sltp = new double[1];


    //TODO: make robust stock validation
    public Order(String ticker,double positionSize,  double[] sltp){
        stock = new Stock(ticker);

        buyPrice = stock.getPrice();
        buyPositionSize = positionSize;
        buyTime = System.currentTimeMillis() / 1000L;;
        this.sltp = sltp;
    }

    public boolean updatePrice(){
        //if this returns true then the position needs to be closed
        currentPrice = stock.getPrice();
        currentTime = System.currentTimeMillis() / 1000L;
        return checkSLTP();

    }

    public boolean checkSLTP(){
        //triggers stop loss take profit and closes order
        //if true closes order
        //bid is used for stop loss and ask is used for take profit
        if (currentPrice[0] <= sltp[0] && sltp[0] != -1){
            return true;
        } else if(currentPrice[1] >= sltp[1] && sltp[1] != -1){
            return true;
        }else{
            return false;
        }
    }

    public Stock getStock(){
        return stock;
    }

    public double getBuyInPrice(){
        return buyPositionSize;
    }

    public double getCurrentSellPrice(){
        return ((currentPrice[0] - buyPrice[1])/currentPrice[0]) * buyPositionSize;
    }

    public double getPnL(){
        return (((currentPrice[0] - buyPrice[1])/buyPrice[1])*buyPositionSize)-buyPositionSize;
    }




}
