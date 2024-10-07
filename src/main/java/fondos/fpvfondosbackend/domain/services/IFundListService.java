package fondos.fpvfondosbackend.domain.services;

import fondos.fpvfondosbackend.aplication.dto.FundDto;

import java.util.List;

public interface IFundListService {
    List<FundDto> getAllFunds();
    FundDto getFundById(String id);
}
