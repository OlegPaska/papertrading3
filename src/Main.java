public class Main {

    public static void main(String[] args){
        APIhandler API = new APIhandler();
        JSONparser jason = new JSONparser();
        double[] bidask = jason.getBidAsk("HSBA.L");
        System.out.println(bidask[0]);
        System.out.println(bidask[1]);


        //System.out.println(API.getQuote(""));

    }
}
