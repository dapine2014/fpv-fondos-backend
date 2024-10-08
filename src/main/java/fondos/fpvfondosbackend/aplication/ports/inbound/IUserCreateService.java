package fondos.fpvfondosbackend.aplication.ports.inbound;

import fondos.fpvfondosbackend.aplication.dto.UserDto;

public interface IUserCreateService {
    UserDto registerUser(UserDto userDto);
}
