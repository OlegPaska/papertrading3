package Market;

import Market.APIhandler;
import Market.JSONparser;

public class Stock {
    JSONparser jason = new JSONparser();
    APIhandler api = new APIhandler();
    String ticker;
    String name;


    //TODO: test the stock validation

    public Stock(String ticker){
        this.ticker = ticker;
        String JSONdata = api.getQuote(ticker, true);
        if(validateTicker(JSONdata)){
            name = jason.find("longName", JSONdata);
        }
    }


    //what is this for??
    public boolean validateTicker(){
        //checks if api fails to get price
        //makes new api call
        String data = api.getQuote(ticker,true);
        if(data != null){
            return true;
        }
        return false;
    }

    public boolean validateTicker(String data){
        //checks if api fails to get price
        //validation is done when constructing stock profile and in the order class
        //api call needed externally
        if(data != null){
            return true;
        }
        return false;
    }

    public double[] getPrice(){
        return jason.getBidAsk(ticker);
    }

    public String getName(){
        return name;
    }

    public String getTicker(){return ticker;}

}
