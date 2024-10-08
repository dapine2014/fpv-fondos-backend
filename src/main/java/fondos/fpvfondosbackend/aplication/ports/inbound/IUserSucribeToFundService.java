package fondos.fpvfondosbackend.aplication.ports.inbound;

import fondos.fpvfondosbackend.aplication.dto.UserDto;

public interface IUserSucribeToFundService {
    UserDto sucribeToFund(String userId, String fundId);
    void unsubscribeFromFund(String userId, String fundId);
}
