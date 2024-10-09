package fondos.fpvfondosbackend.utils;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static fondos.fpvfondosbackend.utils.constant.*;

@Component
public class Validate {

    public  void validateData(FundDto fundDto) {
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

    public void validateData(String data){
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException(DATA_NULL);
        }
    }

    public void validateData(UserDto userDto) {
        Objects.requireNonNull(userDto, USER_NULL);

        if (userDto.getNombre() == null || userDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException(USER_NAME_NULL);
        }

        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException(USER_EMAIL_NULL);
        }

        if (userDto.getTelefono() == null || userDto.getTelefono().isEmpty()) {
            throw new IllegalArgumentException(USER_TEL);
        }

        try {
            Double.parseDouble(String.valueOf(userDto.getSaldo()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(USER_SALDO_NOT_NUMERIC);
        }

    }

    public void validateData(List<FundDto> fundDtoList) {
        if (fundDtoList == null || fundDtoList.isEmpty()) {
            throw new IllegalArgumentException(FUND_LIST_NULL);
        }
    }

    public void validateCategory(String data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException(FUND_SEARCH_NULL);
        }
    }

    public <T> void validateData(T item) {
        if (item == null) {
            throw  new IllegalArgumentException(GENERIC_VALUE_NULL);
        }
        else if (item instanceof Collection) {
            if (((Collection<?>) item).isEmpty()) {
                throw new IllegalArgumentException(GENERIC_VALUE_EMTPY);
            }
        }
        else if (item instanceof String) {
            if (((String) item).isEmpty()) {
                throw new IllegalArgumentException(GENERIC_VALUE_EMTPY);
            }
        }
    }

    private boolean isCategoryValid(String category) {
        return "FPV".equals(category) || "FIC".equals(category);
    }
}
