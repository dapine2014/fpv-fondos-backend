package fondos.fpvfondosbackend.aplication.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import fondos.fpvfondosbackend.aplication.ports.inbound.INotificationService;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImpl implements INotificationService {

    public static final String ACCOUNT_SID = "AC6d772908fbed2a293f12bee25847effb";
    public static final String AUTH_TOKEN = "e555bd329d98633439b6efe561bbe925";


    private final static String EMAIL = "aws.test2025@gmail.com";

    @Override
    public void sendEmail(String to, String subject, String texto) {
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
