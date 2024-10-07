package fondos.fpvfondosbackend.utils;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static fondos.fpvfondosbackend.utils.constant.*;

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

    public void validateFund(List<FundDto> fundDtoList) {
        if (fundDtoList == null || fundDtoList.isEmpty()) {
            throw new IllegalArgumentException(FUND_LIST_NULL);
        }
    }

    public void validateCategory(String data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException(FUND_SEARCH_NULL);
        }
    }

    private boolean isCategoryValid(String category) {
        return "FPV".equals(category) || "FIC".equals(category);
    }
}
