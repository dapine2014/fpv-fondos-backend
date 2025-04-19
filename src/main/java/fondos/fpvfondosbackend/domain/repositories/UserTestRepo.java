package fondos.fpvfondosbackend.domain.repositories;

import fondos.fpvfondosbackend.domain.auxiliary.BaseDynamoDBRepository;
import fondos.fpvfondosbackend.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class UserTestRepo extends BaseDynamoDBRepository<UserEntity,String> {

    protected UserTestRepo(DynamoDbClient dynamoDbClient) {
        super(dynamoDbClient, "usuarios");
    }

    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }
}
