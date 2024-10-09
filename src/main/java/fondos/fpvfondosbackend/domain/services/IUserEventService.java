package fondos.fpvfondosbackend.domain.services;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import java.util.List;

public interface IUserEventService {
    List<UserDto> showAllUsers();
}
