package dev.bpeeva.bookllegro.bll.service;

import dev.bpeeva.bookllegro.db.model.User;
import dev.bpeeva.bookllegro.db.repo.UserRepo;
import dev.bpeeva.bookllegro.util.credentials.CredentialManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepo userRepo;

    /**
     * Fetches a user object from the db
     * @param username the username
     * @return user object
     */
    public Optional<User> getUser(String username) {

        return userRepo.findByUsername(username);
    }

    /**
     * Saves a user to the db
     * @param user the user object
     * @return the optional object
     */
    public Optional<User> saveUser(User user) {
        //Username is taken
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            return Optional.empty();

        //Username isn't email
        if (!CredentialManager.isEmail(user.getUsername()))
            return Optional.empty();

        return Optional.of(userRepo.save(user));
    }

    /**
     * Updates a username
     * @param oldUsername the old username
     * @param newUsername the new username
     */
    public void updateUsername(String oldUsername, String newUsername) {
        Optional<User> user = userRepo.findByUsername(oldUsername);
        if (user.isEmpty()) return;

        //Username is taken
        if (userRepo.findByUsername(newUsername).isPresent()) return;

        //Username isn't email
        if (!CredentialManager.isEmail(newUsername)) return;

        User userToUpdate = user.get();
        userToUpdate.setUsername(newUsername);

        userRepo.save(userToUpdate);
    }

    /**
     * Updates a user password
     * @param username the username
     * @param newPassword the new password (encryption recommended)
     */
    public void updatePassword(String username, String newPassword) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty() || newPassword.isEmpty()) return;

        User userToUpdate = user.get();
        userToUpdate.setPassword(newPassword);

        userRepo.save(userToUpdate);
    }

    /**
     * Deactivate the user account
     * @param username the username
     */
    public void deactivate(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) return;

        User userToDeactivate = user.get();
        userToDeactivate.setActive(false);

        userRepo.save(userToDeactivate);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        if (user.isEmpty() || !user.get().isActive()) throw new UsernameNotFoundException("Username not found!!!");

        return user.get();
    }
}
