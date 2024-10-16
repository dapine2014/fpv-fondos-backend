package fondos.fpvfondosbackend.aplication.ports.inbound;

public interface INotificationService {
    void sendMSM(String to, String subject, String text);
}
