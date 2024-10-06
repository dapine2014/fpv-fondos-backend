package fondos.fpvfondosbackend.aplication.dto;

import lombok.Data;

@Data
public class FundDto {
    private String id;
    private String nombre;
    private double montoMinimo;
    private String categoria;
}
