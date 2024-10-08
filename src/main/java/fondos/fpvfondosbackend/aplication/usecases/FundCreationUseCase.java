package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.ICreateFundService;
import fondos.fpvfondosbackend.domain.services.IFundCommandService;
import fondos.fpvfondosbackend.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FundCreationUseCase implements ICreateFundService {

    private final IFundCommandService fundService;
    private final Validate  validateFund;

    @Autowired
    public FundCreationUseCase(IFundCommandService fundService, Validate validate) {
        this.fundService = fundService;
        this.validateFund = validate;
    }

    @Override
    public FundDto createFund(FundDto fundDto) {
        validateFund.validateData(fundDto);
        return fundService.execute(fundDto);
    }

}
