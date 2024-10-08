package fondos.fpvfondosbackend.aplication.ports.inbound;


import fondos.fpvfondosbackend.aplication.dto.FundDto;

public interface ICreateFundService {
    FundDto createFund(FundDto fundDto);
}
