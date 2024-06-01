# Assortment Endpoint
An assortment endpoint is responsible for reading and updating available assortment.


## Controller Methods
Those are methods that are defined in the ***AssortmentController.java*** file.

### Recommendations
- **Url**: /assortment; 
- **Type**: GET;
- **JSON Body**: { <br>
};
- **Description**: Fetches all products on bookllegro;

### Update Price
- **Role**: SELLER;
- **Url**: /assortment/change-price; 
- **Type**: PATCH;
- **JSON Body**: { <br>
    "title": "BOOK TITLE", <br>
    "price": (BOOK PRICE), <br>
    "seller": "SELLER NAME", <br>
};
- **Description**: Changes a product price;

### Update Amount
- **Role**: SELLER;
- **Url**: /assortment/change-amount; 
- **Type**: PATCH;
- **JSON Body**: { <br>
    "title": "BOOK TITLE", <br>
    "amount": (BOOK AMOUNT), <br>
    "seller": "SELLER NAME", <br>
};
- **Description**: Changes a product amount;

### Add Assortment
- **Role**: SELLER;
- **Url**: /assortment; 
- **Type**: POST;
- **JSON Body**: { <br>
    "title": "BOOK TITLE", <br>
    "price": (BOOK PRICE), <br>
    "amount": (BOOK AMOUNT), <br>
    "seller": "SELLER NAME", <br>
};
- **Description**: Adds a new product on bookllegro;