package fondos.fpvfondosbackend.aplication.ports.outbound;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import java.util.List;

public interface IListUserService {
    List<UserDto> getListUsers();
}
