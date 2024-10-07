package fondos.fpvfondosbackend.domain.entities;

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
    private String id;
    private String nombre;
    private double montoMinimo;
    private String categoria;
}
