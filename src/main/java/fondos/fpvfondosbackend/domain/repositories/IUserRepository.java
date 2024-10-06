package fondos.fpvfondosbackend.domain.repositories;

import fondos.fpvfondosbackend.domain.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<UserEntity, String> {
}
