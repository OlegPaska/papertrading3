package Market;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIhandler {
    private boolean devMode = true;
    String personalKey = "npwKekHzsZuwPJiANYfXismH4nz64g5kObc6K4i0";


    public String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-api-key", personalKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("api call");
        return response.body();
    }
    public String get(String url, boolean devMode) throws IOException, InterruptedException {

        if(devMode) {
            try (
                    FileReader fr = new FileReader("src/data/api_cache.txt");
                    BufferedReader br = new BufferedReader(fr);
            ) {
                //goes through the cache index file and tries to find url and get line after url for api result
                String line = br.readLine();
                while (line != null) {
                    line = br.readLine();
                    if (line.equals(url)) {
                        System.out.println("cached api data accessed [DEV MODE]");
                        return br.readLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error accessing cached data");
            }

            //url hasn't been found in cache so new call is made
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("x-api-key", personalKey)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String apiResult = response.body();
            System.out.println("api call");

            //new call is cached for use later
            try (
                    FileWriter fw = new FileWriter("src/data/api_cache.txt", true);
                    PrintWriter pw = new PrintWriter(fw)
            ) {
                pw.println(url);
                pw.println(apiResult);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return apiResult;
        } else{
            return get(url);
        }


    }



    public String getQuote(String ticker)  {
        try {
            return get("https://yfapi.net/v6/finance/quote?region=GB&lang=en&symbols="+ticker, devMode);

        } catch(Exception e){
            System.out.println("ERROR: error getting API quote data");
            e.printStackTrace();
            return null;
        }
        //this will have useCache set to false but all stock information will have data that has been cached
    }

    //make a new class which uses cached quote data fro

    public String getGraphData(String ticker){
        try {
            return get("https://yfapi.net/v8/finance/chart/"+ticker+"?range=1mo&region=US&interval=1h&lang=en&events=div%2Csplit", devMode);
        } catch(Exception e){
            System.out.println("ERROR: error getting API graph data");
            e.printStackTrace();
            return null;
        }
    }












}



