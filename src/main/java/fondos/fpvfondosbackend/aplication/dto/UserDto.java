package fondos.fpvfondosbackend.aplication.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private double saldo;
    private List<SubscribedFundDto> fondosSuscritos;
    private List<TransactionHistoryDto> transactionHistory;
}
