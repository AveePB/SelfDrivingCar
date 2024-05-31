# Authz Endpoint
An authorization endpoint is responsible for generating and refreshing ***jwts***. Those tokens are later used to access other restricted endpoints.

## Controller Methods
Those are methods that are defined in the ***AuthzController.java*** file.

### Registration 
- **Url(1):** /authz/register/customer; 
- **Url(2):** /authz/register/seller;
- **Type**: POST;
- **JSON Body**: { <br>
    "username": "YOUR_USERNAME", <br>
    "password": "YOUR_PASSWORD"<br>
};
- **Description**: is responsible for creating a bookllegro account;

### Login
- **Url:** /authz/log-in;
- **Type**: POST;
- **JSON Body** { <br>
    "username": "YOUR_USERNAME", <br>
    "password": "YOUR_PASSWORD"<br>
};
- **Description**: is responsible for logging users in;
