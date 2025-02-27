package org.example.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

@DynamoDbBean
public class Cart {

    private String cartId;
    private String customerUserName;
	private String restaurantId;
	private String restaurantName;
	private String itemId;
    private String itemName;
	private String price;
	private String quantity;
	private String totalPrice;
    

    // Default constructor (required by DynamoDB SDK)
    public Cart() {}
    
	@DynamoDbPartitionKey  //Marks this as the Primary Key
    @DynamoDbAttribute("cartId")
	public String getCartId() {
		return cartId;
	}


	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	@DynamoDbAttribute("customerUserName")
	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}
	@DynamoDbAttribute("restaurantId")
	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	@DynamoDbAttribute("restaurantName")
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	@DynamoDbAttribute("itemId")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@DynamoDbAttribute("itemName")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@DynamoDbAttribute("price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	@DynamoDbAttribute("quantity")
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	@DynamoDbAttribute("totalPrice")
	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
}

