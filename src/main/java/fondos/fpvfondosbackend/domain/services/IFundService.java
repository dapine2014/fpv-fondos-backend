package fondos.fpvfondosbackend.domain.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;


public interface IFundService {
    FundDto execute(FundDto fund);
    FundDto updateFund(FundDto fund);
}
