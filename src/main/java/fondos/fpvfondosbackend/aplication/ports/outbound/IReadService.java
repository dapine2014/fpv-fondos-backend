package fondos.fpvfondosbackend.aplication.ports.outbound;

import fondos.fpvfondosbackend.aplication.dto.FundDto;

import java.util.List;

public interface IReadService {
    List<FundDto> readFundAll();
    FundDto readFundById(String id);
}
