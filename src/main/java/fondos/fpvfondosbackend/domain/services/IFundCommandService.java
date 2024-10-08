package fondos.fpvfondosbackend.domain.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.aplication.dto.UserDto;


public interface IFundCommandService {
    FundDto execute(FundDto fund);
    FundDto updateFund(FundDto fund);
    UserDto subscribeToFund(String userId, String fundId);
    void unsubscribeFromFund(String userId, String fundId);
}
