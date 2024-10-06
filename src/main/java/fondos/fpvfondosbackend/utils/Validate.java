package fondos.fpvfondosbackend.utils;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import org.springframework.stereotype.Component;
import java.util.Objects;
import static fondos.fpvfondosbackend.utils.constant.FUND_AMOUNT_NULL;
import static fondos.fpvfondosbackend.utils.constant.FUND_CATEGORY_NULL;
import static fondos.fpvfondosbackend.utils.constant.FUND_NAME_NULL;
import static fondos.fpvfondosbackend.utils.constant.FUND_NULL;

@Component
public class Validate {

    public  void validateFund(FundDto fundDto) {
        Objects.requireNonNull(fundDto, FUND_NULL);

        if (fundDto.getNombre() == null || fundDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException(FUND_NAME_NULL);
        }

        if (fundDto.getMontoMinimo() <= 0) {
            throw new IllegalArgumentException(FUND_AMOUNT_NULL);
        }

        if (!isCategoryValid(fundDto.getCategoria())) {
            throw new IllegalArgumentException(FUND_CATEGORY_NULL);
        }
    }

    private boolean isCategoryValid(String category) {
        return "FPV".equals(category) || "FIC".equals(category);
    }
}
