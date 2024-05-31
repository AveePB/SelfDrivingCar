# User Service
It is responsible for managing users on the platform. We can use it to update accounts and go through data.

## Functions

### `Optional<User>` getUser(String username)
- **Description**: Fetches a user object by username;
- **Input data**: String username;
- **Output data**: Optional\<User>;

### `Optional<User>` saveUser(User user)
- **Description**: Creates or replaces an existing user;
- **Input data**: User user;
- **Output data**: Optional\<User>;

### `void` updateUsername(String oldUsername, String newUsername)
- **Description**: Changes a username;
- **Input data**: String oldUsername, String newUsername;
- **Output data**: None;

### `void` updatePassword(String username, String newPassword)
- **Description**: Changes a user password;
- **Input data**: String username, String newPassword;
- **Output data**: None;

### `void` deactivate(String username)
- **Description**: Permanently deactivates a user;
- **Input data**: String username;
- **Output data**: None;

### `UserDetails` loadUserByUsername(String username)
- **Description**: Fetches a user details object by username. If it fails, then it throws an error ***UsernameNotFoundException***;
- **Input data**: String username;
- **Output data**: UserDetails;