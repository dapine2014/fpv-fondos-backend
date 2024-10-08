package fondos.fpvfondosbackend.aplication.usecases;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.aplication.ports.inbound.IUserCreateService;
import fondos.fpvfondosbackend.domain.services.IUserCommandService;
import fondos.fpvfondosbackend.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreateUseCase implements IUserCreateService {

    private final IUserCommandService userCommandService;
    private final Validate validate;

    @Autowired
    public UserCreateUseCase(IUserCommandService userCommandService, Validate validate) {
        this.userCommandService = userCommandService;
        this.validate = validate;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        validate.validateData(userDto);
        return userCommandService.execute(userDto);
    }
}
