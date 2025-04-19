package fondos.fpvfondosbackend.domain.entities;

import fondos.fpvfondosbackend.domain.anotaciones.DynamoDBField;
import fondos.fpvfondosbackend.domain.anotaciones.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FundEntity implements Serializable {

    @Id
    @DynamoDBField("id")
    private String id;

    @DynamoDBField("nombre")
    private String nombre;

    @DynamoDBField("monto")
    private Double montoMinimo;

    @DynamoDBField("categoria")
    private String categoria;
}
