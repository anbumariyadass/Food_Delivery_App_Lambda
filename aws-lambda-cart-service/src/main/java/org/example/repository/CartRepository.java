package org.example.repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import org.springframework.stereotype.Repository;
import org.example.entity.Cart;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CartRepository {

    private final DynamoDbTable<Cart> cartTable;
    private final DynamoDbClient dynamoDbClient;

    public CartRepository(DynamoDbEnhancedClient enhancedClient, DynamoDbClient dynamoDbClient) {
        this.cartTable = enhancedClient.table("Cart", TableSchema.fromBean(Cart.class));
        this.dynamoDbClient = dynamoDbClient;
    }

    public void save(Cart item) {
    	cartTable.putItem(item);
    }

    public Cart findById(String itemId) {
        return cartTable.getItem(r -> r.key(k -> k.partitionValue(itemId)));
    }

    public void deleteById(String itemId) {
    	cartTable.deleteItem(r -> r.key(k -> k.partitionValue(itemId)));
        
    }

    public List<Cart> findByCustomerName(String customerUserName) {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("Cart")
                .filterExpression("customerUserName = :customerUserName")
                .expressionAttributeValues(Map.of(":customerUserName", AttributeValue.builder().s(customerUserName).build()))
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        return scanResponse.items().stream()
                .map(item -> {
                    Cart cart = new Cart();
                    cart.setCartId(item.get("cartId").s());
                    cart.setCustomerUserName(item.get("customerUserName").s());
                    cart.setRestaurantId(item.get("restaurantId").s());
                    cart.setRestaurantName(item.get("restaurantName").s());
                    cart.setItemId(item.get("itemId").s());
                    cart.setItemName(item.get("itemName").s());
                    cart.setPrice(item.get("price").s());
                    cart.setQuantity(item.get("quantity").s());
                    cart.setTotalPrice(item.get("totalPrice").s());
                    return cart;
                })
                .collect(Collectors.toList());
    }

    public void deleteAllCartsForUser(String customerName) {
        List<Cart> carts = findByCustomerName(customerName);

        for (Cart cart : carts) {
            deleteById(cart.getCartId());
        }
    }
    
}

