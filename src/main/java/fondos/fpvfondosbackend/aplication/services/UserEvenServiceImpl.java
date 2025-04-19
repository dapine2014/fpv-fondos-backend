package fondos.fpvfondosbackend.aplication.services;

import fondos.fpvfondosbackend.aplication.dto.UserDto;
import fondos.fpvfondosbackend.domain.repositories.IUserRepository;
import fondos.fpvfondosbackend.domain.repositories.UserTestRepo;
import fondos.fpvfondosbackend.domain.services.IUserEventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserEvenServiceImpl implements IUserEventService {

    private final UserTestRepo repo;
    private final IUserRepository userRepository;
    private final ModelMapper mapper;

    public UserEvenServiceImpl(UserTestRepo repo, IUserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();;
    }

    @Override
    public List<UserDto> showAllUsers() {
        List<UserDto> users = new ArrayList<>();
       // userRepository.findAll().forEach(user -> users.add(mapper.map(user, UserDto.class)));
          repo.findAll().forEach(user -> users.add(mapper.map(user, UserDto.class)));

        return users;
    }

}
