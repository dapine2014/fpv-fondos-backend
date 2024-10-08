package fondos.fpvfondosbackend.aplication.ports.inbound;

public interface INotificationService {
    void sendEmail(String to, String subject, String text);
}
