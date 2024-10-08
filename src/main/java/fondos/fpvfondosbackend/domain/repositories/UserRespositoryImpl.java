package fondos.fpvfondosbackend.domain.repositories;
import fondos.fpvfondosbackend.domain.auxiliary.SubscribedFund;
import fondos.fpvfondosbackend.domain.auxiliary.TransactionHistory;
import fondos.fpvfondosbackend.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRespositoryImpl implements IUserRepository {

    private final DynamoDbClient dynamoDbClient;

    public UserRespositoryImpl(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public UserEntity saveAll(UserEntity userEntity) {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", AttributeValue.builder().s(userEntity.getId()).build());
        item.put("nombre", AttributeValue.builder().s(userEntity.getNombre()).build());
        item.put("email", AttributeValue.builder().s(userEntity.getEmail()).build());
        item.put("telefono", AttributeValue.builder().s(userEntity.getTelefono()).build());
        item.put("saldo",    AttributeValue.builder().s(String.valueOf(userEntity.getSaldo())).build());
        item.put("fondos",   AttributeValue.builder().l(convertFundSubscriptions(userEntity.getFondosSuscritos())).build());
        item.put("historial",AttributeValue.builder().l(convertTransactionHistory(userEntity.getTransactionHistory())).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("usuarios")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);

        return userEntity;
    }

    @Override
    public UserEntity findUserById(String id) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("usuarios")
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();


        GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
        Map<String, AttributeValue> item = getItemResponse.item();
        if (item != null) {
            return UserEntity.builder()
                    .id(item.get("id").s())
                    .nombre(item.get("nombre").s())
                    .email(item.get("email").s())
                    .telefono(item.get("telefono").s())
                    .saldo(Double.parseDouble(item.get("saldo").s()))
                    .fondosSuscritos(convertToFundSubscriptions(item.get("fondos").l()))
                    .transactionHistory(convertToTransactionHistory(item.get("historial").l()))
                    .build();
        } else {
            return null;
        }
    }

    // Método auxiliar para convertir fundSubscriptions en List<AttributeValue>
    private List<AttributeValue> convertFundSubscriptions(List<SubscribedFund> subscriptions) {
        // Implementar la conversión si FundSubscription es una clase propia
        // Dependiendo de los campos en FundSubscription, se debe crear un mapa con sus valores
        return subscriptions.stream()
                .map(sub -> AttributeValue.builder()
                        .m(Map.of(
                                "fondoId", AttributeValue.builder().s(sub.getFondoId()).build(),
                                "nombreFondo", AttributeValue.builder().s(sub.getNombreFondo()).build(),
                                "monto",AttributeValue.builder().s(String.valueOf(sub.getMonto())).build(),
                                "fechaSuscripcion",AttributeValue.builder().s(sub.getFechaSuscripcion()).build()
                        ))
                        .build())
                .toList();
    }

    // Método auxiliar para convertir transactionHistory en List<AttributeValue>
    private List<AttributeValue> convertTransactionHistory(List<TransactionHistory> transactions) {
        // Implementar la conversión si TransactionEntity es una clase propia
        // Dependiendo de los campos en TransactionEntity, se debe crear un mapa con sus valores
        return transactions.stream()
                .map(th -> AttributeValue.builder()
                        .m(Map.of(
                                "id", AttributeValue.builder().s(th.getFundId()).build(),
                                "fondoId", AttributeValue.builder().s(th.getFundId()).build(),
                                "nombreFondo", AttributeValue.builder().s(th.getFundName()).build(),
                                "evento",AttributeValue.builder().s(th.getType()).build(),
                                "saldo",AttributeValue.builder().s(String.valueOf(th.getAmount())).build(),
                                "fecha",AttributeValue.builder().s(th.getDate()).build()
                        ))
                        .build())
                .toList();
    }


    @Override
    public List<UserEntity> findAll() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("usuarios")
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        List<UserEntity> userEntities = response.items().stream()
                .map(item -> UserEntity.builder()
                        .id(item.get("id").s())
                        .nombre(item.get("nombre").s())
                        .email(item.get("email").s())
                        .telefono(item.get("telefono").s())
                        .saldo(Double.parseDouble(item.get("saldo").s()))
                        .fondosSuscritos( convertToFundSubscriptions(item.get("fondos").l()))
                        .transactionHistory(convertToTransactionHistory(item.get("historial").l()))

                        .build())
                .collect(Collectors.toList());

        return userEntities;
    }

    // Método auxiliar para convertir una lista de AttributeValue en una lista de FundSubscription
    private List<SubscribedFund> convertToFundSubscriptions(List<AttributeValue> attributeValues) {
        List<SubscribedFund> subscriptions = new ArrayList<>();
        for (AttributeValue attributeValue : attributeValues) {
            Map<String, AttributeValue> subscriptionMap = attributeValue.m();
            SubscribedFund subscription = new SubscribedFund();
            subscription.setFondoId(subscriptionMap.get("fundId").s());
            subscription.setNombreFondo(subscriptionMap.get("subscriptionDate").s());
            subscription.setMonto(Double.parseDouble(subscriptionMap.get("monto").s()));
            subscription.setFechaSuscripcion(subscriptionMap.get("fechaSuscripcion").s());
            subscriptions.add(subscription);
        }

        return subscriptions;
    }

    // Método auxiliar para convertir una lista de AttributeValue en una lista de TransactionEntity
    private List<TransactionHistory> convertToTransactionHistory(List<AttributeValue> attributeValues) {
        List<TransactionHistory> transactions = new ArrayList<>();
        for (AttributeValue attributeValue : attributeValues) {
            Map<String, AttributeValue> transactionMap = attributeValue.m();
            TransactionHistory transaction = new TransactionHistory();
            transaction.setId(transactionMap.get("id").s());
            transaction.setFundId(transactionMap.get("fondoId").s());
            transaction.setFundName(transactionMap.get("nombreFondo").s());
            transaction.setType(transactionMap.get("evento").s());
            transaction.setAmount(Double.parseDouble(transactionMap.get("monto").n()));
            transaction.setDate(transactionMap.get("fecha").s());
            transactions.add(transaction);
        }
        return transactions;
    }
}
