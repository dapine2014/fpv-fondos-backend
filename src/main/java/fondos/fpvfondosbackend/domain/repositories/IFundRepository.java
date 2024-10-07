package fondos.fpvfondosbackend.domain.repositories;

import fondos.fpvfondosbackend.domain.entities.FundEntity;

import java.util.List;

public interface IFundRepository {
    FundEntity saveAll(FundEntity fundEntity);
    FundEntity findDynamoById(String id);
    List<FundEntity> findAll();
}
