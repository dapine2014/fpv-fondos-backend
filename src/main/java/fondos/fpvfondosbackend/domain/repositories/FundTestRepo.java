package fondos.fpvfondosbackend.domain.repositories;

import fondos.fpvfondosbackend.domain.auxiliary.BaseDynamoDBRepository;
import fondos.fpvfondosbackend.domain.entities.FundEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class FundTestRepo extends BaseDynamoDBRepository<FundEntity, String> {
    protected FundTestRepo(DynamoDbClient dynamoDbClient) {
        super(dynamoDbClient, "fondos");
    }

    @Override
    protected Class<FundEntity> getEntityClass() {
        return FundEntity.class;
    }
}
