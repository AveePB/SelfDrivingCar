# Database Structure
In this project we use an open-source relational database management system known as MySQL. It's running on the port ***3306***.

## Users Table
| Field     | Type                      | Null | Key |
|-----------|---------------------------|------|-----|
| id        | int                       | NO   | PRI |
| account   | enum('CUSTOMER','SELLER') | NO   |     |
| password  | varchar(255)              | NO   |     |
| username  | varchar(255)              | NO   | UNI |
| token_id  | int                       | YES  | UNI |
| is_active | bit(1)                    | NO   |     |

## Tokens Table
| Field    | Type         | Null | Key |
|----------|--------------|------|-----|
| id       | int          | NO   | PRI |
| raw_form | varchar(255) | NO   | UNI |
| user_id  | int          | YES  | UNI |


## Assortments Table
| Field     | Type         | Null | Key |
|-----------|--------------|------|-----|
| id        | int          | NO   | PRI |
| amount    | int          | NO   |     |
| price     | double       | NO   |     |
| title     | varchar(255) | NO   |     |
| seller_id | int          | YES  | MUL |