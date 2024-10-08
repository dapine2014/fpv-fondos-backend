package fondos.fpvfondosbackend.domain.auxiliary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionHistory implements Serializable {

    private String id;
    private String fundId;
    private String fundName;
    private String type; // subscription or cancellation
    private double amount;
    private String date;
}
