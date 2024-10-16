package fondos.fpvfondosbackend.aplication.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import fondos.fpvfondosbackend.aplication.ports.inbound.INotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImpl implements INotificationService {

    @Value("${account.sid}")
    private String ACCOUNT_SID;

    @Value("${auth.token}")
    private String AUTH_TOKEN;


    private final static String EMAIL = "aws.test2025@gmail.com";

    @Override
    public void sendMSM(String to, String subject, String texto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); //"+573203009984"

        Message message = Message
                .creator(
                        new PhoneNumber("+57"+to),
                        new PhoneNumber("+19893033616"),
                        texto
                )
                .create();

        System.out.println(message.getSid());
    }
}
