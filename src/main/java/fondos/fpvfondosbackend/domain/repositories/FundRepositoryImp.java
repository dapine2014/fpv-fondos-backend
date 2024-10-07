package fondos.fpvfondosbackend.domain.repositories;

import fondos.fpvfondosbackend.domain.entities.FundEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FundRepositoryImp implements IFundRepository {

    private final DynamoDbClient dynamoDbClient;

    public FundRepositoryImp(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public FundEntity saveAll(FundEntity fundEntity) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(fundEntity.getId()).build());
        item.put("nombre", AttributeValue.builder().s(fundEntity.getNombre()).build());
        item.put("monto", AttributeValue.builder().s(String.valueOf(fundEntity.getMontoMinimo())).build());
        item.put("categoria", AttributeValue.builder().s(fundEntity.getCategoria()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("fondos")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);

        return fundEntity;
    }

    @Override
    public List<FundEntity> findAll() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("fondos")
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        List<FundEntity> fundEntities = response.items().stream()
                .map(item -> FundEntity.builder()
                        .id(item.get("id").s())
                        .nombre(item.get("nombre").s())
                        .montoMinimo(Double.parseDouble(item.get("monto").s()))
                        .categoria(item.get("categoria").s())
                        .build())
                .collect(Collectors.toList());
        return fundEntities;
    }

    @Override
    public FundEntity findDynamoById(String id) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("fondos")
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();

        GetItemResponse response = dynamoDbClient.getItem(getItemRequest);
        if (response.hasItem()) {
            Map<String, AttributeValue> item = response.item();
            return FundEntity.builder()
                    .id(item.get("id").s())
                    .nombre(item.get("nombre").s())
                    .montoMinimo(Double.parseDouble(item.get("monto").s()))
                    .categoria(item.get("categoria").s())
                    .build();
        } else {
            return null;
        }
    }
}
