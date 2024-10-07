package fondos.fpvfondosbackend.domain.entities;

import fondos.fpvfondosbackend.domain.auxiliary.SubscribedFund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity implements Serializable {

    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private double saldo;
    private List<SubscribedFund> fondosSuscritos;
}
