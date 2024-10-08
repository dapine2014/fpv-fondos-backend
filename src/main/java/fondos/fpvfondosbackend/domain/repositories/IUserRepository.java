package fondos.fpvfondosbackend.domain.repositories;
import fondos.fpvfondosbackend.domain.entities.UserEntity;

import java.util.List;

public interface IUserRepository  {
    UserEntity saveAll(UserEntity fundEntity);
    UserEntity findUserById(String id);
    List<UserEntity> findAll();
}
