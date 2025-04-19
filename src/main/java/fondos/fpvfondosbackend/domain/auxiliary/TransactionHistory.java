package fondos.fpvfondosbackend.domain.auxiliary;

import fondos.fpvfondosbackend.domain.anotaciones.DynamoDBSubField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;



@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionHistory implements Serializable {

    @DynamoDBSubField("id")
    private String id;

    @DynamoDBSubField("fondoId")
    private String fundId;

    @DynamoDBSubField("nombreFondo")
    private String fundName;

    @DynamoDBSubField("evento")
    private String type; // subscription or cancellation

    @DynamoDBSubField("saldo")
    private double amount;

    @DynamoDBSubField("fecha")
    private String date;
}
