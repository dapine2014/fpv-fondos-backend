package fondos.fpvfondosbackend.domain.repositories;

import fondos.fpvfondosbackend.domain.entities.FundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFundRepository extends MongoRepository<FundEntity, String> {
    boolean existsById(String name);
}
