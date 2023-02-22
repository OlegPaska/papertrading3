package Market;

public class ShortOrder extends Order{

    public ShortOrder(String ticker,double positionSize,  double[] sltp){
        //setting variables
        super(ticker, positionSize, sltp);


    }

    //this is the one called by the portfolio class on startup when reading from file
    public ShortOrder(String ticker, double buyPrice, long buyTime, double buyPositionSize,double[] sltp){
        //setting variables
        super(ticker,buyPrice, buyTime, buyPositionSize, sltp);

        //calling method to save order
    }

    //these two are just simple reversed
    @Override
    public double getPnL() {
        return super.getPnL()*-1;
    }

    @Override
    public double getPnLPercent() {
        return super.getPnLPercent() *-1;
    }
}
