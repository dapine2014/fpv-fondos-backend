package fondos.fpvfondosbackend.aplication.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FundDto {
    private String id;
    private String nombre;
    private double montoMinimo;
    private String categoria;
}
