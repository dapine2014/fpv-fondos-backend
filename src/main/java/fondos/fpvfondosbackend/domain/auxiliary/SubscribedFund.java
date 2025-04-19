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
@Getter
@Setter
public class SubscribedFund implements Serializable {
    @DynamoDBSubField("fondoId")
    private String fondoId;

    @DynamoDBSubField("nombreFondo")
    private String nombreFondo;

    @DynamoDBSubField("monto")
    private double monto;

    @DynamoDBSubField("fechaSuscripcion")
    private String fechaSuscripcion;
}
