package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.aplication.ports.outbound.IListUserService;
import fondos.fpvfondosbackend.domain.services.IUserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserReadUseCase implements IListUserService {

    private final IUserEventService userEventService;

    @Autowired
    public UserReadUseCase(IUserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @Override
    public List<UserDto> getListUsers() {
        return userEventService.showAllUsers();
    }
}
