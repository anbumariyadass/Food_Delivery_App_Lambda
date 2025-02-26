package org.example.repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import org.springframework.stereotype.Repository;
import org.example.entity.Cart;

@Repository
public class CartRepository {

    private final DynamoDbTable<Cart> cartTable;
    

    public CartRepository(DynamoDbEnhancedClient enhancedClient) {
        this.cartTable = enhancedClient.table("Cart", TableSchema.fromBean(Cart.class));  //Explicit table name
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
    
}

