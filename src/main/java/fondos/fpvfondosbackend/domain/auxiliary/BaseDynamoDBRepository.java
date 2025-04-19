package fondos.fpvfondosbackend.domain.auxiliary;

import fondos.fpvfondosbackend.domain.anotaciones.DynamoDBField;
import fondos.fpvfondosbackend.domain.anotaciones.DynamoDBSubField;
import fondos.fpvfondosbackend.domain.anotaciones.Id;
import org.springframework.util.Assert;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public abstract class BaseDynamoDBRepository<T, ID> {
    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    protected abstract Class<T> getEntityClass();

    protected BaseDynamoDBRepository(DynamoDbClient dynamoDbClient, String tableName) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = tableName;
    }

    private <T> Map<String, AttributeValue> objectToMap(T object) {
        Map<String, AttributeValue> item = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null) {
                    String fieldName = field.getName();
                    AttributeValue attributeValue = convertValueToAttributeValue(value);
                    item.put(fieldName, attributeValue);
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        return item;
    }

    private AttributeValue convertValueToAttributeValue(Object value) {
        if (value instanceof String) {

            return AttributeValue.builder().s(value.toString()).build();
        } else if (value instanceof Number) {

            return AttributeValue.builder().n(value.toString()).build();
        } else if (value instanceof Boolean) {

            return AttributeValue.builder().bool((Boolean) value).build();
        } else if (value instanceof List) {

            return AttributeValue.builder().l(convertListToAttributeValueList((List<T>) value)).build();
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String formattedDate = sdf.format((Date) value);

            return AttributeValue.builder().s(formattedDate).build();
        } else {
            return AttributeValue.builder().s(value.toString()).build();
        }
    }

    private List<AttributeValue> convertListToAttributeValueList(List<T> list) {
        List<AttributeValue> attributeValues = new ArrayList<>();
        list.stream()
                .forEach(item -> {
                    List<Field> fields = Arrays.stream(item.getClass().getDeclaredFields()).toList();
                    Map<String, AttributeValue> attr = new HashMap<>();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(item);
                            if (value != null) {
                                String fieldName = field.getName();
                                attr.put(fieldName, AttributeValue.builder().s(value.toString()).build());
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    attributeValues.add(AttributeValue.builder().m(attr).build());
                });
        return attributeValues;
    }

    private Field getIdField(Class<T> clazz) {
        System.out.println("Buscando campo @Id en la clase: " + clazz.getName());
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElse(null);

    }

    private Class<?> getListItemType(Field field) {
        if (List.class.isAssignableFrom(field.getType())) {
            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            return (Class<?>) listType.getActualTypeArguments()[0];
        }
        return null;
    }

    private T mapItemToEntity(Map<String, AttributeValue> item) {
        Class<T> entityClass = getEntityClass();
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();

            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);

                // Obtener el nombre del campo de DynamoDB desde la anotación, si existe
                String dynamoDBFieldName = field.isAnnotationPresent(DynamoDBField.class)
                        ? field.getAnnotation(DynamoDBField.class).value()
                        : field.getName();

                // Obtener el valor del campo desde el mapa DynamoDB usando el nombre correspondiente
                AttributeValue attributeValue = item.get(dynamoDBFieldName);

                if (attributeValue != null) {
                    // Convertir el valor de AttributeValue al tipo del campo
                    Class<?> listItemType = List.class.isAssignableFrom(field.getType()) ? getListItemType(field) : null;
                    Object value = convertAttributeValueToField(attributeValue, field.getType(), listItemType);
                    field.set(entity, value);
                }
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping DynamoDB item to entity", e);
        }
    }

    private Object convertAttributeValueToField(AttributeValue attributeValue, Class<?> fieldType, Class<?> itemType) {
        if (fieldType == String.class) {
            return attributeValue.s();
        } else if (Number.class.isAssignableFrom(fieldType) || fieldType.isPrimitive()) {
            if (attributeValue.s() != null) {
                if (fieldType == Double.class || fieldType == double.class) {
                    return Double.parseDouble(attributeValue.s());
                } else if (fieldType == Integer.class || fieldType == int.class) {
                    return Integer.parseInt(attributeValue.n());
                } else if (fieldType == Long.class || fieldType == long.class) {
                    return Long.parseLong(attributeValue.n());
                }
            }
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return attributeValue.bool();
        } else if (fieldType == Date.class && attributeValue.s() != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                return sdf.parse(attributeValue.s());
            } catch (Exception e) {
                throw new RuntimeException("Error parsing date: " + attributeValue.s(), e);
            }
        } else if (List.class.isAssignableFrom(fieldType) && attributeValue.l() != null && itemType != null) {
            // Inicializar una lista concreta y mapear cada elemento de la lista
            List<Object> list = new ArrayList<>();
            for (AttributeValue listItem : attributeValue.l()) {
                // Convierte cada elemento de la lista al tipo correspondiente
                Object element = convertAttributeValueToField(listItem, itemType, null);
                list.add(element);
            }
            return list;
        } else if (attributeValue.m() != null) {
            // Si es un mapa, convertimos el mapa a la entidad correspondiente
            return convertMapToEntity(attributeValue.m(), fieldType);
        }

        return null; // Si el tipo no coincide con ninguno de los manejados
    }


    private <E> E convertMapToEntity(Map<String, AttributeValue> map, Class<E> entityType) {
        try {
            E entity = entityType.getDeclaredConstructor().newInstance();

            for (Field field : entityType.getDeclaredFields()) {
                field.setAccessible(true);

                // Nombre de campo en DynamoDB según la anotación
                String fieldName = field.isAnnotationPresent(DynamoDBField.class)
                        ? field.getAnnotation(DynamoDBField.class).value()
                        : field.getName();

                AttributeValue attributeValue = map.get(fieldName);

                if (attributeValue != null) {
                    Object value;

                    // Verificar si el campo es una lista y si contiene subobjetos con DynamoDBSubField
                    if (List.class.isAssignableFrom(field.getType())) {
                        Class<?> listItemType = getListItemType(field);

                        if (hasDynamoDBSubFields(listItemType)) {
                            // Si los elementos de la lista tienen DynamoDBSubField, mapear cada subobjeto
                            value = attributeValue.l().stream()
                                    .map(attr -> convertSubMapToEntity(attr.m(), listItemType))
                                    .toList();
                        } else {
                            // Conversión para listas de tipos básicos
                            value = convertAttributeValueToField(attributeValue, field.getType(), listItemType);
                        }
                    } else {
                        // Conversión para tipos básicos
                        value = convertAttributeValueToField(attributeValue, field.getType(), null);
                    }

                    field.set(entity, value);
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping DynamoDB item to entity", e);
        }
    }


    // Método para convertir un mapa de DynamoDB en un subelemento de la lista
    private <S> S convertSubMapToEntity(Map<String, AttributeValue> map, Class<S> subEntityType) {
        try {
            S subEntity = subEntityType.getDeclaredConstructor().newInstance();

            for (Field subField : subEntityType.getDeclaredFields()) {
                subField.setAccessible(true);

                // Nombre del subcampo según la anotación DynamoDBSubField
                String subFieldName = subField.isAnnotationPresent(DynamoDBSubField.class)
                        ? subField.getAnnotation(DynamoDBSubField.class).value()
                        : subField.getName();

                AttributeValue attributeValue = map.get(subFieldName);

                if (attributeValue != null) {
                    // Convertir el valor al tipo de campo adecuado
                    Object value = convertAttributeValueToField(attributeValue, subField.getType(), getListItemType(subField));
                    subField.set(subEntity, value);
                }
            }
            return subEntity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping DynamoDB sub-item to entity", e);
        }
    }

    // Método auxiliar para detectar si una clase tiene campos anotados con @DynamoDBSubField
    private boolean hasDynamoDBSubFields(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(DynamoDBSubField.class)) {
                return true;
            }
        }
        return false;
    }


    /// public method
    public T save(T entity) {
        Map<String, AttributeValue> objectToMap = objectToMap(entity);
        String name = entity.getClass().getSimpleName();
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(objectToMap)
                .build();
        dynamoDbClient.putItem(request);

        return entity;
    }

    public Optional<T> findById(ID id) {
        Assert.notNull(id, "The given id must not be null");
        Field idField = getIdField(this.getEntityClass());
        // Validar si se encontró el campo @Id
        if (idField == null) {
            throw new RuntimeException("No field annotated with @Id found in class: " + id.getClass().getSimpleName());
        }

        String fieldName = idField.getName();

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(Collections.singletonMap(fieldName, AttributeValue.builder().s(String.valueOf(id)).build()))
                .build();

        GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
        Map<String, AttributeValue> item = getItemResponse.item();
        if (item != null && !item.isEmpty()) {
            T entity = mapItemToEntity(item);
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    public void Delete(ID id) {
        Assert.notNull(id, "the id could not be empty");
        Field idField = getIdField(this.getEntityClass());
        if (idField == null) {
            throw new RuntimeException("No field annotated with @Id found in class: " + id.getClass().getSimpleName());
        }
        String fieldName = idField.getName();

        try {
            // Construir solo la clave primaria necesaria para borrar
            Map<String, AttributeValue> deleteKey = Collections.singletonMap(fieldName, AttributeValue.builder().s(String.valueOf(id)).build());

            DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                    .tableName(tableName)
                    .key(deleteKey) // Usar solo la clave primaria para borrar
                    .build();

            dynamoDbClient.deleteItem(deleteReq);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting DynamoDB item", e);
        }
    }

    public List<T> findAll() {
        Field idField = getIdField(this.getEntityClass());
        if (idField == null) {
            throw new RuntimeException("No field annotated with @Id found in class: ");
        }
        String fieldName = idField.getName();
        try {
            // Crear la solicitud de escaneo con el nombre de la tabla
            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName(tableName)
                    .build();

            // Ejecutar el escaneo
            ScanResponse response = dynamoDbClient.scan(scanRequest);

            // Convertir los elementos del escaneo en instancias de T
            List<T> data = response.items().stream()
                    .map(this::mapItemToEntity) // Convertir cada ítem a una instancia de T
                    .toList();

            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error finding DynamoDB items", e);
        }
    }
}