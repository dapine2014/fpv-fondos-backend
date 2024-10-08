package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.ports.outbound.IReadService;
import fondos.fpvfondosbackend.domain.services.IFundEventService;
import fondos.fpvfondosbackend.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FundReadUseCase implements IReadService {

    private final IFundEventService fundListService;
    private final Validate validate;

    @Autowired
    public FundReadUseCase(IFundEventService fundListService, Validate validate) {
        this.fundListService = fundListService;
        this.validate = validate;
    }

    @Override
    public List<FundDto> readFundAll() {
        List<FundDto> fundDos = fundListService.getAllFunds();
        validate.validateData(fundDos);

        return fundDos;
    }

    @Override
    public FundDto readFundById(String id) {
        validate.validateCategory(id);
        return fundListService.getFundById(id);
    }
}
