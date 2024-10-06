package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.ISubscriptionService;
import fondos.fpvfondosbackend.domain.services.IFundService;
import fondos.fpvfondosbackend.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FundCreationUseCase implements ISubscriptionService {

    private final IFundService fundService;
    private final Validate  validateFund;

    @Autowired
    public FundCreationUseCase(IFundService fundService, Validate validate) {
        this.fundService = fundService;
        this.validateFund = validate;
    }

    @Override
    public FundDto createSubscription(FundDto fundDto) {
        validateFund.validateFund(fundDto);
        return fundService.execute(fundDto);
    }

}
