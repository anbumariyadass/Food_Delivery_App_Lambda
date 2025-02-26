package org.example.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

@DynamoDbBean
public class Cart {

    private String cartId;
    private String customerName;
    private String itemName;
    

    // Default constructor (required by DynamoDB SDK)
    public Cart() {}
    
	public Cart(String cartId, String customerName, String itemName) {
		this.cartId = cartId;
		this.customerName = customerName;
		this.itemName = itemName;
	}



	@DynamoDbPartitionKey  //Marks this as the Primary Key
    @DynamoDbAttribute("cartId")
	public String getCartId() {
		return cartId;
	}


	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	@DynamoDbAttribute("customerName")
	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@DynamoDbAttribute("itemName")
	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
    
}

