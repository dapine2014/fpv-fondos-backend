package fondos.fpvfondosbackend.aplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribedFundDto {
    private String fondoId;
    private String nombreFondo;
    private double monto;
    private String fechaSuscripcion;
}
