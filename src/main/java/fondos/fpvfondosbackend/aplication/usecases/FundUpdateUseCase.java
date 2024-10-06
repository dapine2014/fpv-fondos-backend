package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IReplaceService;
import fondos.fpvfondosbackend.domain.services.IFundService;
import fondos.fpvfondosbackend.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FundUpdateUseCase implements IReplaceService {

    private final IFundService fundService;
    private final Validate  validateFund;

    @Autowired
    public FundUpdateUseCase(IFundService fundService, Validate value) {
        this.fundService = fundService;
        this.validateFund = value;
    }

    @Override
    public FundDto replaceFund(FundDto fundDto) {
        validateFund.validateFund(fundDto);
        return fundService.updateFund(fundDto);
    }
}
