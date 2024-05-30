# Token Service
Token service is used to manage, verify and generate ***json web tokens***.

**Json Web Token** (JWT): is an open standard (RFC 7519) that defines a compact and selfcontained way for transmitting info between parties as JSON object. [Learn more about jwts.](https://jwt.io/introduction)

## Constants

### `SignatureAlgorithm` SIGNATURE_ALGORITHM
It provides a digital signature function. This mechanism allows us to attach a signature to the messages.

### `String` SECRET_KEY
It is the key that is used to encrypt and decrypt data.

### `int` TOKEN_LIFESPAN
It indicates how long tokens are valid since they are generated.

## Functions

### `String` getPrincipalName(JWT jwt)
- **Description**: Fetches a principal name from the token;
- **Input data**: JWT jwt;
- **Output data**: String;

### `Date` getExpirationDate(JWT jwt)
- **Description**: Fetches an expiration date from the token;
- **Input data**: JWT jwt;
- **Output data**: Date;

### `boolean` isValid(JWT jwt, String username)
- **Description**: Validates a token by checking a principal name and an expiration date;
- **Input data**: JWT jwt, String username;
- **Output data**: boolean;

### `JWT` generateJWT(User user)
- **Description**: Generates a jwt and saves it to the database;
- **Input data**: User user;
- **Output data**: JWT;