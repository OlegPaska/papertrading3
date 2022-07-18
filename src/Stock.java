public class Stock {
    JSONparser jason = new JSONparser();
    APIhandler api = new APIhandler();
    String ticker;

    //TODO: make it check wether the ticker is valid by making new method
    //TODO: add all the functions from the uml diagram
    //TODO: make first api call build a profile of the stock w/ name etc
    public Stock(String ticker){
        this.ticker = ticker;
    }

    public boolean validateTicker(){
        //checks if api fails to get price

        if(api.getQuote(ticker) != null){
            return true;
        }
        return false;
    }
}
