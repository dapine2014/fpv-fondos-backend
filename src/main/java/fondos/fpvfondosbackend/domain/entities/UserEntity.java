package fondos.fpvfondosbackend.domain.entities;

import fondos.fpvfondosbackend.domain.anotaciones.DynamoDBField;
import fondos.fpvfondosbackend.domain.anotaciones.Id;
import fondos.fpvfondosbackend.domain.auxiliary.SubscribedFund;
import fondos.fpvfondosbackend.domain.auxiliary.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity implements Serializable {

    @Id
    @DynamoDBField("id")
    private String id;

    @DynamoDBField("nombre")
    private String nombre;

    @DynamoDBField("email")
    private String email;

    @DynamoDBField("telefono")
    private String telefono;

    @DynamoDBField("saldo")
    private double saldo;

    @DynamoDBField("fondosSuscritos")
    private List<SubscribedFund> fondosSuscritos;

    @DynamoDBField("transactionHistory")
    private List<TransactionHistory> transactionHistory;
}
