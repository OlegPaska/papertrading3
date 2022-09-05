package Market;

import java.util.Arrays;

public class JSONparser {


    APIhandler api = new APIhandler();


    //just realised that there is a much easier way of doing this but this way looks more complex.
    //other way: just search for "\""+keyword+"\":"
    public String find(String keyword, String JSON){

        int cursor = 0;
        int iterate = 1;


            while(JSON.contains(keyword)) {

                cursor = JSON.indexOf(keyword);

                //gets length of keyword answer
                while (JSON.charAt(cursor + keyword.length() + 2 + iterate) != ',') {
                    iterate++;
                }

                if (JSON.charAt(cursor + keyword.length() + 1) == ':' && JSON.charAt(cursor - 1) == '"') {
                    //returns successful parse
                    //removed and all the other stuff in findIndex() isn't here because the first instance of keyword found always gives the desired outcome
                    return JSON.substring(cursor + keyword.length() + 2, cursor + keyword.length() + 2 + iterate);
                } else{
                    //removes all searched area
                    JSON = JSON.substring(cursor + keyword.length() + 2, JSON.length());
                }
            }
            System.out.println("ERROR: unable to find keyword <"+keyword+"> in JSON");
            return "ERROR: JSON parse not found";



    };

    //same thing for find() applies here. it could also just return the whole JSON array.
    //actual spaghetti code
    public int[] findIndex(String keyword, String JSON){
        int cursor;
        int iterate = 1;
        int removed = 0;


        while(JSON.contains(keyword)) {

            cursor = JSON.indexOf(keyword);

            //gets length of keyword answer
            while (JSON.charAt(cursor + keyword.length() + 2 + iterate) != ',') {
                iterate++;
            }

            if (JSON.charAt(cursor + keyword.length() + 1) == ':' && JSON.charAt(cursor - 1) == '"') {
                //returns beginning and end of json array

                //this is done so the first ']' indexOf() finds is the one closing the JSON array just found
                int start = cursor+keyword.length()+3+removed;
                removed += cursor + keyword.length() + 3;
                JSON = JSON.substring(cursor+keyword.length()+3, JSON.length());

                return new int[] {start, JSON.indexOf(']')+removed};
            } else{
                //removes all searched area and tracks how much was removed since it doesn't remove from the original JSON variable only the local one
                removed += cursor + keyword.length() + 2;
                JSON = JSON.substring(cursor + keyword.length() + 2, JSON.length());

            }
        }
        System.out.println("ERROR: unable to find keyword <" + keyword + "> in JSON");
        return new int[] {-1,-1};
    };

    public double[] getBidAsk(String stock){
        String JSON = api.getQuote(stock);
        String bid = find("bid", JSON);
        String ask = find("ask", JSON);
        //error checking
        if (isDouble(bid) && isDouble(ask)) {
            return new double[] {Double.parseDouble(bid), Double.parseDouble(ask)};
        }else {
            //return -1 for error
            return new double[]{-1, -1};
        }

    }

    //returns double[][]
    public double[][] getGraphData(String ticker){
        //graph data returned in format: {[open],[close],[high],[low]}
        String JSON = api.getGraphData(ticker);
        int[] openIndexes = findIndex("open", JSON);
        int[] closeIndexes = findIndex("close", JSON);
        int[] highIndexes = findIndex("high", JSON);
        int[] lowIndexes = findIndex("low", JSON);
        int[] timeStampIndex = findIndex("timestamp", JSON);
        double[] open = Arrays.stream(JSON.substring(openIndexes[0], openIndexes[1]).split(",")).mapToDouble(Double::parseDouble).toArray();
        double[] close = Arrays.stream(JSON.substring(closeIndexes[0], closeIndexes[1]).split(",")).mapToDouble(Double::parseDouble).toArray();
        double[] high = Arrays.stream(JSON.substring(highIndexes[0], highIndexes[1]).split(",")).mapToDouble(Double::parseDouble).toArray();
        double[] low = Arrays.stream(JSON.substring(lowIndexes[0], lowIndexes[1]).split(",")).mapToDouble(Double::parseDouble).toArray();
        double[] timeStamp = Arrays.stream(JSON.substring(timeStampIndex[0], timeStampIndex[1]).split(",")).mapToDouble(Double::parseDouble).toArray();
        return new double[][] {open, close, high, low, timeStamp};


    }

    //i copied this
    public boolean isDouble(String val){
        if (val == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(val);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
