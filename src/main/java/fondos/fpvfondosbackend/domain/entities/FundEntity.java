package fondos.fpvfondosbackend.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "fondos")
public class FundEntity implements Serializable {

    @Id
    private String id;
    private String nombre;
    private double montoMinimo;
    private String categoria;
}
