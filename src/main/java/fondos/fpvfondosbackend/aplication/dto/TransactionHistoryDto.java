package fondos.fpvfondosbackend.aplication.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class TransactionHistoryDto {
    private String id;
    private String fundId;
    private String fundName;
    private String type; // subscription or cancellation
    private double amount;
    private String date;
}
