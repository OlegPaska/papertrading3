import Market.JSONparser;
import Market.Portfolio;
import GUI.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Login.LoginHandler;



import java.util.Properties;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import com.sendgrid.*;
import java.io.IOException;
public class Main {


    public static void main(String[] args) throws InterruptedException {
/*
        Email from = new Email("olegpaska.2005@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("olegpaska.2005@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("SG.2eLSFu-WQUGDlDUIWARNWA.lDxSk4AGEdYLSsAX5W1p2dTWz0LKBmDZoddUC-CR2Xo");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        */


        //LoginHandler login = new LoginHandler();


//        Portfolio portfolio = new Portfolio(30000, "john");
//        portfolio.buyOrder("AAPL", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("Z", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("TSLA", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("goog",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("tdoc", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("cvna",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("AAPL", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("Z", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("TSLA", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("goog",5000,  new double[]{-1,-1});
//        portfolio.buyOrder("tdoc", 5000, new double[]{-1,-1});
//        portfolio.buyOrder("cvna",5000,  new double[]{-1,-1});
//        GUIhandler gui = new GUIhandler();
//        gui.mainScreen(portfolio);

        GUIhandler guihandler = new GUIhandler();
        guihandler.login();
        //Basic basic = new Basic("aapl");

//        Runnable tick = new Runnable() {
//
//        };
//
//
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        executor.scheduleAtFixedRate(tick, 0, 60, TimeUnit.SECONDS);


        //

    }
}



