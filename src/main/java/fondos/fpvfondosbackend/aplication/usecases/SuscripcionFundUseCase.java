package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IUserSucribeToFundService;
import fondos.fpvfondosbackend.domain.services.IFundCommandService;
import fondos.fpvfondosbackend.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuscripcionFundUseCase implements IUserSucribeToFundService {

    private final IFundCommandService fundService;
    private final Validate validateFund;

    @Autowired
    public SuscripcionFundUseCase(IFundCommandService fundService, Validate validateFund) {
        this.fundService = fundService;
        this.validateFund = validateFund;
    }

    @Override
    public UserDto sucribeToFund(String userId, String fundId) {
         validateFund.validateData(userId);
         validateFund.validateData(fundId);

         return fundService.subscribeToFund(userId, fundId);
    }

    @Override
    public void unsubscribeFromFund(String userId, String fundId) {
        validateFund.validateData(userId);
        validateFund.validateData(fundId);

        fundService.unsubscribeFromFund(userId,fundId);
    }
}
