# User Scopes
Scopes provide a mechanism to limit the access of the client (the requesting party) to API resources. The scopes depend on the role of a user in the system. We divide users into two types (***customer*** & ***seller***).

### Account Scopes
The account scopes are included in both a customer and a seller.
- **CHANGE_USERNAME ("update:username")**: to change username;
- **CHANGE_PASSOWRD ("update:password")**: to change passowrd;
- **DEACTIVATE_ACCOUNT ("delete:account")**: to deactivate account;

## Customer Specified Scopes 
The customer account is permitted to browser through products and place orders for wanted books.

### Customer Product Scopes
- **GET_PRODUCT ("read:product")**: to get product basic info;

### Customer Order Scopes
- **PLACE_ORDER ("create:order")**: to place an order for products;
- **CANCEL_ORDER ("delete:order")**: to cancel an order;
- **GET_ORDER ("read:order")**: to get order basic info;


## Seller Specified Scopes
The seller account has a huge variety of allowed operations on the platform. They can manage their assortment and received orders.

### Seller Product Scopes
- **GET_PRODUCT ("read:product")**: to get product basic info;
- **ADD_ASSORTMENT ("create:assortment")**: to add an assortment;
- **RESTOCK_ASSORTMENT ("update:assortment")**: to restock an assortment;
- **REMOVE_ASSORTMENT ("delete:assortment")**: to remove an assortment;

### Seller Order Scopes
- **GET_ORDER ("read:order")**: to get order basic info;
- **UPDATE_ORDER ("update:order")**: to update an order;
- **CANCEL_ORDER ("delete:order")**: to cancel an order;
