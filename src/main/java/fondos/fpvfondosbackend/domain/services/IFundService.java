package fondos.fpvfondosbackend.domain.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;
import fondos.fpvfondosbackend.domain.entities.FundEntity;

public interface IFundService {
    FundDto execute(FundDto fund);
    FundDto updateFund(FundDto fund);
}
