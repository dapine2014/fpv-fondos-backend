package fondos.fpvfondosbackend.aplication.ports.inbound;

import fondos.fpvfondosbackend.aplication.dto.FundDto;

public interface ISubscriptionService {
    FundDto createSubscription(FundDto fundDto);
}
