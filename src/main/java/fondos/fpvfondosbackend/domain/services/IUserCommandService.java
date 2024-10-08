package fondos.fpvfondosbackend.domain.services;

import fondos.fpvfondosbackend.aplication.dto.UserDto;

public interface IUserCommandService {
    UserDto execute(UserDto userDto);
    UserDto updateUser(UserDto userDto);
}
