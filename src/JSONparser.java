import java.util.ArrayList;

public class JSONparser {


    APIhandler api = new APIhandler();

    public String find(String stock, String keyword, String JSON){
        APIhandler api = new APIhandler();

        int cursor = 0;
        int iterate = 1;


            do {
                if(JSON.contains(keyword)) {
                    cursor = JSON.indexOf(keyword);

                    //gets length of keyword answer
                    while (JSON.charAt(cursor + keyword.length() + 2 + iterate) != ',') {
                        iterate++;
                    }

                    if (JSON.charAt(cursor + keyword.length() + 1) == ':') {
                        //returns successful parse
                        return JSON.substring(cursor + keyword.length() + 2, cursor + keyword.length() + 2 + iterate);
                    } else{
                        //removes all searched area
                        JSON = JSON.substring(cursor + keyword.length() + 2, JSON.length());
                    }
                } else{
                    System.out.println("ERROR: unable to find keyword in JSON");
                    cursor = -1;
                    return "ERROR: JSON parse not found";
                }
            }while(JSON.charAt(cursor + keyword.length() + 1) != ':' || cursor == -1);

            return "idk its not supposed to go here";

    };

    public double[] getBidAsk(String stock){
        String JSON = api.getQuote(stock);
        return new double[] {Double.parseDouble(find(stock, "bid", JSON)), Double.parseDouble(find(stock, "ask", JSON))};
    }



}
