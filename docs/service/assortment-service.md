# Assortment Service
Assortment service is used to manage offered products by the bookllegro service.

## Functions 

### `List<AssortmentDTO>` getAssortments()
- **Description**: fetches all assortments on bookllegro;
- **Input data**: None;
- **Output data**: List;

### `List<AssortmentDTO>` getAssortments(User seller)
- **Description**: fetches all assortments offered by the seller;
- **Input data**: User seller;
- **Output data**: List;

### `boolean` updatePrice(String title, Double newPrice, User seller)
- **Description**: changes a price of the book;
- **Input data**: String title, Double newPrice, User seller;
- **Output data**: boolean;

### `boolean` updateAmount(String title, Integer amount, User seller)
- **Description**: changes an amount of the book copies;
- **Input data**: String title, Integer amount, User seller;
- **Output data**: boolean;

### `boolean` saveAssortment(Assortment assortment)
- **Description**: adds a new assortment to the service;
- **Input data**: Assortment assortment;
- **Output data**: boolean;

### `void` remove(String title, User seller)
- **Description**: removes a book from bookllegro;
- **Input data**: String title, User seller;
- **Output data**: None;