package fondos.fpvfondosbackend.aplication.ports.inbound;

import fondos.fpvfondosbackend.aplication.dto.FundDto;

public interface IReplaceService {
    FundDto replaceFund(FundDto fundDto);
}
