package fondos.fpvfondosbackend.domain.auxiliary;

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
    private String fondoId;
    private String nombreFondo;
    private double monto;
    private String fechaSuscripcion;
}
