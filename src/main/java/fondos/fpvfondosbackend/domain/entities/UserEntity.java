package fondos.fpvfondosbackend.domain.entities;


import fondos.fpvfondosbackend.domain.auxiliary.SubscribedFund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "usuarios")
public class UserEntity implements Serializable {

    @Id
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private double saldo;
    private List<SubscribedFund> fondosSuscritos;
}
