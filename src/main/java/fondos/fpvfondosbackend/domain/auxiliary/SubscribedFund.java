package fondos.fpvfondosbackend.domain.auxiliary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribedFund {
    private String fondoId;
    private String nombreFondo;
    private double monto;
    private String fechaSuscripcion;
}
