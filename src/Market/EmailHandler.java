package Market;

import com.sendgrid.*;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class EmailHandler {
    public void send(String message){
        Email from = new Email("olegpaska.2005@gmail.com");
        String subject = "Paper Trading notification";
        Email to = new Email("olegpaska.2005@gmail.com");
        Content content = new Content("text/plain", message);
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
    }
}
